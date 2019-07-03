package com.userPanel.log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

public class HTTPRequestPoster {
    private static final Logger logger = Logger
            .getLogger(HTTPRequestPoster.class);

    /**
     * Sends an HTTP GET request to a url
     * 
     * @param endpoint
     *            - The URL of the server. (Example:
     *            " http://www.yahoo.com/search")
     * @param requestParameters
     *            - all the request parameters (Example:
     *            "param1=val1&param2=val2"). Note: This method will add the
     *            question mark (?) to the request - DO NOT add it yourself
     * @return - The response from the end point
     */
    public static String sendGetRequest(String endpoint,
            String requestParameters) {
        if (logger.isInfoEnabled())
            logger.info("Entry: Class: HTTPRequestPoster, method: sendGetRequest()");

        String result = "";
        if (endpoint.startsWith("http://")) {
            // Send a GET request to the servlet
            try {
                // Construct data
                StringBuffer data = new StringBuffer();

                // Send data
                String urlStr = endpoint;
                if (requestParameters != null && requestParameters.length() > 0) {
                    urlStr += requestParameters;
                }
                URL url = new URL(urlStr);
                // System.out.println(url);
                URLConnection conn = url.openConnection();

                // Get the response
                BufferedReader rd = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                rd.close();

                result = sb.toString();
                // System.out.println(result);
            } catch (Exception e) {
                if (logger.isDebugEnabled())
                    logger.debug("Exit: Class: HTTPRequestPoster, method: sendGetRequest()"
                            + e.getMessage());

                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Reads data from the data reader and posts it to a server via POST
     * request. data - The data you want to send endpoint - The server's address
     * output - writes the server's response to output
     * 
     * @throws Exception
     */
    public static void postData(Reader data, URL endpoint, Writer output)
            throws Exception {
        if (logger.isInfoEnabled())
            logger.info("Entry: Class: HTTPRequestPoster, method: postData()");

        HttpURLConnection urlc = null;
        try {
            urlc = (HttpURLConnection) endpoint.openConnection();
            try {
                urlc.setRequestMethod("POST");
            } catch (ProtocolException e) {
                if (logger.isDebugEnabled())
                    logger.debug("Exit: Class: HTTPRequestPoster, method: postData() :: ProtocolException ::> "
                            + e.getMessage());

                throw new Exception(
                        "Shouldn't happen: HttpURLConnection doesn't support POST??",
                        e);
            }
            urlc.setDoOutput(true);
            urlc.setDoInput(true);
            urlc.setUseCaches(false);
            urlc.setAllowUserInteraction(false);
            urlc.setRequestProperty("Content-type", "text/xml; charset="
                    + "UTF-8");

            OutputStream out = urlc.getOutputStream();

            try {
                Writer writer = new OutputStreamWriter(out, "UTF-8");
                pipe(data, writer);
                writer.close();
            } catch (IOException e) {
                if (logger.isDebugEnabled())
                    logger.debug("Exit: Class: HTTPRequestPoster, method: postData() :: IOException ::> "
                            + e.getMessage());

                throw new Exception("IOException while posting data", e);
            } finally {
                if (out != null)
                    out.close();
            }

            InputStream in = urlc.getInputStream();
            try {
                Reader reader = new InputStreamReader(in);
                pipe(reader, output);
                reader.close();
            } catch (IOException e) {
                if (logger.isDebugEnabled())
                    logger.debug("Exit: Class: HTTPRequestPoster, method: postData() :: IOException ::> "
                            + e.getMessage());

                throw new Exception("IOException while reading response", e);
            } finally {
                if (in != null)
                    in.close();
            }

        } catch (IOException e) {
            if (logger.isDebugEnabled())
                logger.debug("Exit: Class: HTTPRequestPoster, method: postData() :: IOException ::> "
                        + e.getMessage());

            throw new Exception("Connection error (is server running at "
                    + endpoint + " ?): " + e);
        } finally {
            if (urlc != null)
                urlc.disconnect();
        }
    }

    /**
     * Pipes everything from the reader to the writer via a buffer
     */
    private static void pipe(Reader reader, Writer writer) throws IOException {
        if (logger.isInfoEnabled())
            logger.info("Entry: Class: HTTPRequestPoster, method: pipe()");

        char[] buf = new char[1024];
        int read = 0;
        while ((read = reader.read(buf)) >= 0) {
            writer.write(buf, 0, read);
        }
        writer.flush();
    }

    /**
     * calling post service new code
     * 
     */

    public static String callService(String urlStr, String[] paramName,
            String[] paramValue) {

        StringBuilder response = new StringBuilder();
        URL url;
        try {
            url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            // Create the form content
            OutputStream out = conn.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            for (int i = 0; i < paramName.length; i++) {
                writer.write(paramName[i]);
                writer.write("=");
                writer.write(URLEncoder.encode(paramValue[i], "UTF-8"));
                writer.write("&");
            }
            writer.close();
            out.close();
            if (conn.getResponseCode() != 200) {
                throw new IOException(conn.getResponseMessage());
            }
            // Buffer the result into a string
            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            response = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
            }
            rd.close();

            conn.disconnect();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return response.toString();
    }

}
