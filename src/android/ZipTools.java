/*
 	Author: Vishal Rajpal
 	Filename: ExtractZipFilePlugin.java
 	Created Date: 22-01-2013
 	Modified Date: 22-01-2013
 */

package com.kcg.ziptools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.json.JSONArray;
import org.json.JSONException;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;

public class ZipTools extends CordovaPlugin {
	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		if (action.equals("Extract")) {
			String filename = args.getString(0);
			Extract(filename, callbackContext);
			return true;
		}

		return false;
	}

	private static void Extract(String strZipSrc, CallbackContext callbackContext) {

		try {

			File fSourceZip = new File(strZipSrc);
			String zipPath = strZipSrc.substring(0, strZipSrc.length() - 4);
			File temp = new File(zipPath);
			temp.mkdir();

			ZipFile zipFile = new ZipFile(fSourceZip);
			Enumeration<? extends ZipEntry> e = zipFile.entries();

			while (e.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) e.nextElement();
				File destinationFilePath = new File(zipPath, entry.getName());

				// create directories if required.
				destinationFilePath.getParentFile().mkdirs();

				// if the entry is directory, leave it. Otherwise extract it.
				if (entry.isDirectory()) {
					continue;
				} else {

					BufferedInputStream bis = new BufferedInputStream(
							zipFile.getInputStream(entry));

					int b;
					byte buffer[] = new byte[1024];

					FileOutputStream fos = new FileOutputStream(
							destinationFilePath);
					BufferedOutputStream bos = new BufferedOutputStream(fos,
							1024);

					while ((b = bis.read(buffer, 0, 1024)) != -1) {
						bos.write(buffer, 0, b);
					}

					// flush the output stream and close it.
					bos.flush();
					bos.close();

					// close the input stream.
					bis.close();
				}

			}
		} catch (ZipException e1) {
			callbackContext.error(PluginResult.Status.MALFORMED_URL_EXCEPTION
					.toString());
			return;
		}

		catch (IOException e1) {
			callbackContext.error(PluginResult.Status.IO_EXCEPTION.toString());
			return;
		}

		callbackContext.success(strZipSrc);

	}

}