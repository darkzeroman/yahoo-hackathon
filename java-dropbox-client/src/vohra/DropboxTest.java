package vohra;

import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JOptionPane;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.DropboxInputStream;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.RequestTokenPair;
import com.dropbox.client2.session.Session.AccessType;
import com.dropbox.client2.session.WebAuthSession;
import com.dropbox.client2.session.WebAuthSession.WebAuthInfo;

/**
 * A very basic dropbox example.
 * 
 * @author mjrb5
 */
public class DropboxTest {

	private static final String APP_KEY = "dw26io99wowf87d";
	private static final String APP_SECRET = "pt2bvxgm1cdtfa7";
	private static final AccessType ACCESS_TYPE = AccessType.APP_FOLDER;
	private static DropboxAPI<WebAuthSession> mDBApi;
	private static final String key = "clwtd8hqi5vqnwp";
	private static final String secret = "jdkqv0t9yg2bgr6";
	private static final String folderName = "test";

	public static void main(String[] args) throws Exception {
		getAPI();
	}

	public static void testAccess() throws DropboxException {
		AccessTokenPair accessToken = new AccessTokenPair(key, secret);
		AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
		WebAuthSession session = new WebAuthSession(appKeys, ACCESS_TYPE,
				accessToken);
		session.setAccessTokenPair(accessToken);
		DropboxAPI<WebAuthSession> api = new DropboxAPI<WebAuthSession>(session);

		DropboxAPI.Account account = null;
		try {
			account = api.accountInfo();
		} catch (DropboxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("User Name: " + account.displayName + " "
				+ account.uid);

		System.out.println();
		System.out.print("Uploading file...");
		String fileContents = "Hello World!";
		ByteArrayInputStream inputStream = new ByteArrayInputStream(
				fileContents.getBytes());
		Entry newEntry = api.putFile("/testing1.txt", inputStream,
				fileContents.length(), null, null);
		System.out.println("Done. \nRevision of file: " + newEntry.rev);
	}

	public static DropboxAPI<WebAuthSession> getAPI() {
		AccessTokenPair accessToken = new AccessTokenPair(key, secret);
		AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
		WebAuthSession session = new WebAuthSession(appKeys, ACCESS_TYPE,
				accessToken);
		session.setAccessTokenPair(accessToken);
		DropboxAPI<WebAuthSession> api = new DropboxAPI<WebAuthSession>(session);

		DropboxAPI.Account account = null;
		try {
			account = api.accountInfo();
		} catch (DropboxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("User Name: " + account.displayName + " "
		// + account.uid);
		// String fileName = "hw4-sol.pdf";
		// DropboxInputStream dis = api.getFileStream(fileName, null);
		// writeToFile(dis, fileName);

		return api;
	}

	public static void writeToFile(InputStream inputStream, String fileName) {
		try {
			File f = new File(folderName + "/" + fileName);

			OutputStream out = new FileOutputStream(f);
			byte buf[] = new byte[1024];
			int len;
			while ((len = inputStream.read(buf)) > 0)
				out.write(buf, 0, len);
			out.close();
			inputStream.close();
			System.out.println("\nFile is created....");

		} catch (IOException e) {
			System.out.println("error");
		}

	}

	public static InputStream readFile(String path) {
		try {
			File fileIn = new File(path);
			File fileOut = new File("target.txt");

			FileInputStream streamIn = new FileInputStream(fileIn);
			return streamIn;
		} catch (FileNotFoundException e) {
			System.err.println("FileCopy: " + e);
		} catch (IOException e) {
			System.err.println("FileCopy: " + e);
		}
		return null;

	}

	public static void getAccess() throws DropboxException,
			MalformedURLException, IOException, URISyntaxException {

		AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
		WebAuthSession session = new WebAuthSession(appKeys, ACCESS_TYPE);
		WebAuthInfo authInfo = session.getAuthInfo();

		RequestTokenPair pair = authInfo.requestTokenPair;
		String url = authInfo.url;

		Desktop.getDesktop().browse(new URL(url).toURI());
		JOptionPane.showMessageDialog(null,
				"Press ok to continue once you have authenticated.");
		session.retrieveWebAccessToken(pair);

		AccessTokenPair tokens = session.getAccessTokenPair();
		System.out
				.println("Use this token pair in future so you don't have to re-authenticate each time:");
		System.out.println("Key token: " + tokens.key);
		System.out.println("Secret token: " + tokens.secret);

		mDBApi = new DropboxAPI<WebAuthSession>(session);

		System.out.println();
		System.out.print("Uploading file...");
		String fileContents = "Hello World!";
		ByteArrayInputStream inputStream = new ByteArrayInputStream(
				fileContents.getBytes());
		Entry newEntry = mDBApi.putFile("/testing.txt", inputStream,
				fileContents.length(), null, null);
		System.out.println("Done. \nRevision of file: " + newEntry.rev);
	}
}