package com.amardeep.simplenotes.util;

import java.io.ByteArrayOutputStream;

import com.amardeep.simplenotes.constants.SimpleNotesConstants;

import android.graphics.Bitmap;
import android.util.Base64;

public class ImageUtil {
	public static String encodeString(Bitmap bitmap) {
		StringBuilder encodedImage = new StringBuilder();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, SimpleNotesConstants.IMAGE_QUALITY, baos);
		byte[] imageBytes = baos.toByteArray();
		encodedImage.append("data:image/jpeg;base64,");
		encodedImage.append(Base64.encodeToString(imageBytes, Base64.NO_WRAP));
		return encodedImage.toString();
	}
}
