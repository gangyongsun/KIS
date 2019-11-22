package cn.com.goldwind.kis.mmseg.interfa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.goldwind.kis.mmseg.main.ComplexSeg;
import cn.com.goldwind.kis.mmseg.main.Dictionary;
import cn.com.goldwind.kis.mmseg.main.MMSeg;
import cn.com.goldwind.kis.mmseg.main.Seg;
import cn.com.goldwind.kis.mmseg.main.Word;

/**
 * 分词对外部的接口，生成过程中需，设置好分词词典的路径名称
 * 
 * @author Frank Adolf
 *
 */

public class MMsegInterface {

	/**
	 * error logger information
	 */
	private static final Logger logInfo = Logger.getLogger(MMsegInterface.class.getName());

	// 异步形式
	private static MMsegInterface instance = null;

	public synchronized static MMsegInterface getInstance() {
		if (instance == null) {
			instance = new MMsegInterface();

		}
		return instance;
	}

	/**
	 * words segment handle
	 */
	private Seg wordSegHandle = null;

	private MMsegInterface() {
		this.init();
	}

	private void init() {

		// segment dictionary file name
		Dictionary dic = Dictionary.getInstance();
		// Forward maximum matching
		// this.wordSegHandle = new SimpleSeg(dic);
		this.wordSegHandle = new ComplexSeg(dic);
	}

	/**
	 * only store the word ends position
	 * 
	 * @param text the input string
	 * @return the segment tag pos array
	 */
	public int[] textWordSegLable(String text) {
		if (text.isEmpty()) {
			return null;
		}

		int[] segLable = new int[text.length()];
		for (int i = 0; i < text.length(); i++) {
			segLable[i] = 0;
		}

		MMSeg mmSeg = new MMSeg(new StringReader(text), this.wordSegHandle);
		Word word = null;
		int tag = 0;

		try {
			while ((word = mmSeg.next()) != null) {
				/*
				 * System.out.print(word.getString()+" -> "+word.getStartOffset()); //offset +=
				 * word.length;
				 * System.out.println(", "+word.getEndOffset()+", "+word.getType());
				 */
				segLable[tag] = word.getEndOffset();
				tag++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return segLable;
	}

	/**
	 * @param text the input string
	 * @return the the original text and the segment words
	 */
	public List<String> textWordSegToList(String text) {
		if (text.isEmpty()) {
			return null;
		}

		MMSeg mmSeg = new MMSeg(new StringReader(text), this.wordSegHandle);
		Word word = null;
		// StringBuffer retBuffer = new StringBuffer();
		List<String> ret_list = new ArrayList<String>();
//		ret_list.add(text);
		try {
			while (null != (word = mmSeg.next())) {
				/*
				 * System.out.print(word.getString()+" -> "+word.getStartOffset()); //offset +=
				 * word.length;
				 * System.out.println(", "+word.getEndOffset()+", "+word.getType());
				 */
				// retBuffer.append(word.getString() + "$$");
				
				if (!"".equals(word.getString().trim())) {
					ret_list.add(word.getString().trim());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// return retBuffer.toString();
		return ret_list;
	}

	/**
	 * store the word ends position and the sentences ends position
	 * 
	 * @param text the input string
	 * @return the segment tag pos array
	 */
	private int[] textWordSegLable(String text, int[] senPos) {
		if (text.isEmpty()) {
			return null;
		}

		if (senPos == null) {
			return this.textWordSegLable(text);
		}

		int[] segLable = new int[text.length()];
		for (int i = 0; i < text.length(); i++) {
			segLable[i] = 0;
		}

		MMSeg mmSeg = new MMSeg(new StringReader(text), this.wordSegHandle);
		Word word = null;
		int tag = 0;
		int index = 0;
		try {
			while ((word = mmSeg.next()) != null) {
				// System.out.print(word.getString()+" -> "+word.getStartOffset());
				// offset += word.length;
				// System.out.println(", "+word.getEndOffset()+", "+word.getType());

				if (word.getType() == "punct") {
					senPos[index] = word.getEndOffset();
					index++;
				}
				segLable[tag] = word.getEndOffset();
				tag++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return segLable;
	}

	/**
	 * output segment result into file
	 * 
	 * @param text    the text line String
	 * @param outfile the output file path name
	 */
	private boolean listSegmentWord(String text, String outfile) {
		if (text.isEmpty() || outfile.isEmpty()) {
			return false;
		}

		MMSeg fmmSeg = new MMSeg(new StringReader(text), this.wordSegHandle);
		Word word = null;

		try {
			FileOutputStream fos = new FileOutputStream(new File(outfile));
			OutputStreamWriter os = new OutputStreamWriter(fos, "UTF-8");
			BufferedWriter bw = new BufferedWriter(os);
			while ((word = fmmSeg.next()) != null) {

				bw.write(word.toString() + " / ");
			}
			bw.close();
			os.close();
			fos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * deal and change file content into text， only for test
	 * 
	 * @param filePath the using file absolute path
	 * @return the result string all the file lines
	 */
	private String loadingTextFromFile(String filePath) {

		if (filePath.isEmpty()) {
			logInfo.info("runing loadingTextFromFile() function is error!");
			return null;
		}

		String fileContant = "";
		try {
			FileInputStream fis = new FileInputStream(filePath);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader br = new BufferedReader(isr);

			String line = "";
			while ((line = br.readLine()) != null) {
				if (false == line.equals("")) {
					String realStr = line.trim();
					realStr = realStr.replaceAll("　", "");
					fileContant += realStr;
				}
			}
			br.close();
			isr.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileContant;
	}

	/**
	 * 处理文中的空格，回车换行的特殊符号
	 * 
	 * @param text
	 * @return
	 */
	private String proceSpecialCharacter(String text) {
		String destStr = "";
		if (text.isEmpty() == true || text == null) {
			return destStr;
		}
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(text);
		destStr = m.replaceAll("");

		return destStr;
	}

}
