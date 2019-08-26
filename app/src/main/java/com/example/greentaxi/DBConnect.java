package com.example.greentaxi;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

// 통합 데이터베이스

public class DBConnect {

    static class DBConnections extends AsyncTask<String, Void, String> {
        HttpURLConnection conn;
        String m_cookies = "";

        // 각 액티비티에서 IP 주소값과 쿼리문을 받아와서 POST방식으로 전송
        // 웹에서 받는 형식은 query= 다음 사용할 쿼리문
        // ex) 회원가입같은경우 query=(insert 구문)

        @Override
        protected String doInBackground(String... params) {
            try {

                // 사용처에서 IP, query 문 순으로 데이터를 보내주기때문에 IP ,query 순으로 받는다
                String connIP = params[0];
                String query = params[1];

                //받은 IP를 URL 에 세팅해준다
                URL url = new URL(connIP);
                // 받은 url로 커넥션을 만들어준다
                conn = (HttpURLConnection) url.openConnection();

                // 읽는, 연결시간 5초
                conn.setReadTimeout(5000);
                conn.setConnectTimeout(5000);
                // post 방식
                conn.setRequestMethod("POST");
                // outputstream, inputstream 활성화
                conn.setDoOutput(true);
                conn.setDoInput(true);

                // 공유객체의 쿠키값을 가져와서 널인지 아닌지 판단하고 쿠키 값 전송

                if(CookieData.getInstance().getCookie() != null){
                    conn.setRequestProperty("cookie", CookieData.getInstance().getCookie());
                    Log.d("쿠키전달 했다 저장댔다?!!!?", CookieData.getInstance().getCookie());
                }

                // 연결 시작
                conn.connect();

                // 연결된 데이터를 os에 담아주고
                OutputStream os = conn.getOutputStream();
                // query 문을 데이터 세팅해준다(인코딩까지
                os.write(query.getBytes("UTF-8"));
                //비워준다(보낸다
                os.flush();
                //닫고(안닫아도 동작은되지만 닫는게좋음
                os.close();

                // 보냈기때문에 응답이 왔으니 is에 담는다
                InputStream is = conn.getInputStream();
                // 담은걸 보기위해 sb를 생성하고
                StringBuilder sb = new StringBuilder();

                //우선 받은 데이터를 br에 인코딩해서 담아준다
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                String check;

                // 받은 데이터를 빌때까지 sb에 넣어주고
                while ((check = br.readLine()) != null) {
                    sb.append(check + "\n");
                }

                // 쿠키 값을 공유 객체에 넣어서 사용
                // 넣지않으면 로그인에서 현재 클래스를 선언하여 쿠키를 받았을 때
                // 로그인 페이지에서는 사용가능하지만 액티비티를 건너서
                // 구글맵에서 현재 클래스를 이어 선언했을 때 쿠키 값이 null

                m_cookies = conn.getHeaderField("Set-Cookie");
                CookieData.getInstance().setCookie(m_cookies);

                // 주석 달린 부분은 쿠키값 전체를 출력, 위는 마지막 한줄만 출력
                /*
                Map<String, List<String>> imap = conn.getHeaderFields( ) ;

                if( imap.containsKey( "Set-Cookie" ) )
                {
                    List<String> lString = imap.get( "Set-Cookie" ) ;
                    for( int i = 0 ; i < lString.size() ; i++ ) {
                        m_cookies += lString.get( i ) ;
                    }
                    Log.e("MuseOn Cookie ",m_cookies);
                }
                m_cookies.trim();
                CookieData.getInstance().setCookie(m_cookies);
                */
                // 다담아서 비어있으므로 닫고
                br.close();

                //연결종료
                conn.disconnect();

                // 받아왔기때문에 sb에 값이 있으니 리턴해서 사용한다
                Log.d("반환 값 ", sb.toString());
                return sb.toString();
            } catch (
                    MalformedURLException e) {
                Log.e("MalformedURLException: ", e.getMessage());
            } catch (
                    UnsupportedEncodingException e) {
                e.printStackTrace();
                Log.e("UnsupportedEncodingE: ", e.getMessage());
            } catch (
                    ProtocolException e) {
                e.printStackTrace();
                Log.e("ProtocolException : ", e.getMessage());
            } catch (
                    IOException e) {
                e.printStackTrace();
                Log.e("IOException : ", e.getMessage());  //이무진병신 아 ㅇㅋㅇㅋ
            }

            return null;
        }

        // 반환된 sb를 텍스트에 출력
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }
    }
}