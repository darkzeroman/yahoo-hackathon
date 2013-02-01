package examples;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.exception.DropboxServerException;
import com.dropbox.client2.session.Session;
import com.dropbox.client2.session.WebAuthSession;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.DropboxAPI;

import java.io.IOException;
import java.util.List;

public class CommandLineAuth
{
    public static void main(String[] args)
        throws DropboxException, IOException
    {
        String server;
        AppKeyPair appKey;
        AccessTokenPair accessToken;
        if (args.length == 2) {
            appKey = new AppKeyPair(args[0], args[1]);
            accessToken = null;
        }
        else if (args.length == 4) {
            appKey = new AppKeyPair(args[0], args[1]);
            accessToken = new AccessTokenPair(args[2], args[3]);
        }
        else {
            System.err.println("Usage: COMMAND app-key app-secret [ access-token access-token-secret ]");
            System.exit(1); return;
        }

        // ----------------------------------------------------
        // Do web-based OAuth

        WebAuthSession was = new WebAuthSession(appKey, Session.AccessType.DROPBOX);

        if (accessToken == null) {
            WebAuthSession.WebAuthInfo info = was.getAuthInfo();
            System.out.println("1. Go to: " + info.url);
            System.out.println("2. Allow access to this app.");
            System.out.println("3. Press ENTER.");
            while (System.in.read() != '\n') {}

            String userId = was.retrieveWebAccessToken(info.requestTokenPair);
            System.out.println("User ID: " + userId);
            System.out.println("Access Key: " + was.getAccessTokenPair().key);
            System.out.println("Access Secret " + was.getAccessTokenPair().secret);
        }
        else {
            was.setAccessTokenPair(accessToken);
        }

        DropboxAPI<WebAuthSession> api = new DropboxAPI<WebAuthSession>(was);

        DropboxAPI.Account account = api.accountInfo();
        System.out.println("User Name: " + account.displayName);
    }
}