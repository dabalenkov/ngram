package org.jcryptool.analysis.ngram.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.eclipse.ui.IEditorReference;
import org.jcryptool.core.operations.editors.EditorsManager;
import org.jcryptool.core.operations.editors.EditorUtils;

public class ConvencienceTools {
	
	public static String getCurrentEditorText() {
		InputStream activeEditorReference = EditorsManager.getInstance().getActiveEditorContentInputStream();
		return EditorUtils.inputStreamToString(activeEditorReference);
	}
	
	public static String getTextFromFile(File file) throws FileNotFoundException {
		return EditorUtils.inputStreamToString(new FileInputStream(file));
	}
	
}
