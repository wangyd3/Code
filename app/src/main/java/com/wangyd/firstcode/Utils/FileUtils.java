package com.wangyd.firstcode.Utils;

import android.content.Context;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 文件的常用操作
 * 
 * @author wangyd
 * 
 */
public class FileUtils {

	public static final String TAG = "FileUtils";

	/**
	 * 从文件中读取对象 /data中
	 * 
	 * @param fileName
	 * @return
	 */
	public static Object readObj(Context context, String fileName) {

		Object obj = null;
		ObjectInputStream input = null;
		try {
			input = new ObjectInputStream(context.openFileInput(fileName));
			obj = input.readObject();
		} catch (Exception e) {
			DBG.log(TAG, "readObj:", e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return obj;
	}

	/**
	 * 把对象写入文件中 /data中
	 * 
	 * @param obj
	 * @param fileName
	 */
	public static void writeObj(Context context, Object obj, String fileName) {
		ObjectOutputStream output = null;
		try {
			// 如果没有存在则会创建
			output = new ObjectOutputStream(context.openFileOutput(fileName,
					Context.MODE_PRIVATE));
			output.writeObject(obj);
		} catch (Exception e) {
			DBG.log(TAG, "writeObj:", e);
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
				}
			}
		}

	}

	/**
	 * 记录在data中
	 * 
	 * @param context
	 * @param fileName
	 * @param string
	 */
	public static boolean record(Context context, String fileName, String string) {
		boolean flag = false;
		FileOutputStream output = null;

		try {
			// 如果没有存在则会创建
			output = context.openFileOutput(fileName, Context.MODE_PRIVATE
					| Context.MODE_APPEND);
			output.write(string.getBytes());
			flag = true;
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					System.out.println(e);
				}
			}
		}

		return flag;
	}

	/**
	 * 记录在SD卡中
	 * 
	 * @param fileName
	 * @param string
	 */
	public static boolean record(String fileName, String string) {
		boolean flag = false;
		FileOutputStream fileOutputStream = null;
		File file = new File(Environment.getExternalStorageDirectory(),
				fileName);// 获得SD卡所在的路径
		DBG.log(TAG, "record path =" + file.getAbsolutePath());
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) { // 判断SD卡是否可用
			try {
				fileOutputStream = new FileOutputStream(file, true);
				fileOutputStream.write(string.getBytes());
				flag = true;
			} catch (FileNotFoundException e) {
				DBG.log(TAG, "FileNotFoundException:", e);
			} catch (IOException e) {
				DBG.log(TAG, "IOException:", e);
			} finally {
				if (fileOutputStream != null) {
					try {
						fileOutputStream.close();
					} catch (IOException e) {
						DBG.log(TAG, "IOException:", e);
					}
				}
			}
		}
		DBG.log(TAG, "record = " + flag);
		return flag;
	}

	/**
	 * 读取文件所有内容/data中
	 * 
	 * @param context
	 * @param fileName
	 */
	public static String read(Context context, String fileName) {
		FileInputStream input = null;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			// 如果没有存在,则不会创建
			input = context.openFileInput(fileName);

			byte[] data = new byte[1024];
			int len = 0;
			while ((len = input.read(data)) != -1) {
				outputStream.write(data, 0, len);
			}

		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					System.out.println(e);
				}
			}
		}

		return new String(outputStream.toByteArray());
	}

	/**
	 * 读取文件所有内容/SD卡中
	 * 
	 * @param fileName
	 */
	public static String read(String fileName) {
		FileInputStream inputStream = null;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		File file = new File(Environment.getExternalStorageDirectory(),
				fileName);
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			try {
				inputStream = new FileInputStream(file);
				int len = 0;
				byte[] data = new byte[1024];
				while ((len = inputStream.read(data)) != -1) {
					outputStream.write(data, 0, len);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}
		return new String(outputStream.toByteArray());
	}

	public static boolean deleteFile(Context context, String fileName) {
		boolean flag = false;
		File file = new File(context.getFilesDir(), fileName);
		if (file.exists()) {
			flag = file.delete();
		}
		return flag;
	}

	public static boolean deleteFile(String fileName) {
		boolean flag = false;

		File file = new File(Environment.getExternalStorageDirectory(),
				fileName);
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			if (file.exists()) {
				flag = file.delete();
			}
		}
		return flag;
	}

	public static boolean createFile(File file) throws IOException {
		if (!file.exists()) {
			makeDir(file.getParentFile());
		}
		return file.createNewFile();
	}

	public static void makeDir(File dir) {
		if (!dir.getParentFile().exists()) {
			makeDir(dir.getParentFile());
		}
		dir.mkdir();
	}

	/**
	 * echo
	 * 
	 * @param fileName
	 * @param string
	 */
	public static boolean write(String fileName, String string) {
		byte[] data = string.getBytes();
		DBG.log(TAG, "lenth = " + data.length);
		for (int i = 0; i < data.length; i++)
			DBG.log(TAG, i + " = " + data[i]);
		boolean flag = false;
		FileOutputStream fileOutputStream = null;
		File file = new File(fileName);// 获得SD卡所在的路径
		DBG.log(TAG, "record path =" + file.getAbsolutePath());
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) { // 判断SD卡是否可用
			try {
				fileOutputStream = new FileOutputStream(file, false);
				fileOutputStream.write(string.getBytes());
				flag = true;
			} catch (FileNotFoundException e) {
				DBG.log(TAG, "FileNotFoundException:", e);
			} catch (IOException e) {
				DBG.log(TAG, "IOException:", e);
			} finally {
				if (fileOutputStream != null) {
					try {
						fileOutputStream.close();
					} catch (IOException e) {
						DBG.log(TAG, "IOException:", e);
					}
				}
			}
		}
		DBG.log(TAG, "write = " + flag);
		return flag;
	}

	/**
	 * 读取文件所有内容/SD卡中
	 * 
	 * @param fileName
	 */
	public static String readx(String fileName) {
		DBG.log(TAG, DBG._FUNC_());
		FileInputStream inputStream = null;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		File file = new File(fileName);

		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			try {
				inputStream = new FileInputStream(file);
				int len = 0;
				byte[] data = new byte[1024];
				while ((len = inputStream.read(data)) != -1) {
					DBG.log(TAG, "len=" + len);
					for (int i = 0; i < len; i++)
						DBG.log(TAG, i + " = " + data[i]);
					outputStream.write(data, 0, len);
				}
			} catch (FileNotFoundException e) {
				DBG.log(TAG, DBG._FUNC_(), e);
			} catch (IOException e) {
				DBG.log(TAG, DBG._FUNC_(), e);
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						DBG.log(TAG, DBG._FUNC_(), e);
					}
				}
			}

		}
		return new String(outputStream.toByteArray());
	}

}
