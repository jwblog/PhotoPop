package com.jarrah.photo;

/**
 * @author jarrah
 * request code for startActivityForResult
 */
public interface ReqeustCode {
	/**
	 * sys photo gallery request code 
	 */
	public static final int FROM_GALLERY = 0xa2014;
	
	
	/**
	 * capture request code
	 */
	public static final int FROM_CAPTURE = FROM_GALLERY + 1; 
	
	
	/**
	 * sys crop request code
	 */
	public static final int FROM_CROP = FROM_CAPTURE + 1;
}
