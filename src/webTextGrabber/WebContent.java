package webTextGrabber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class WebContent {
 // hotelId, hotelUrl, cityId can be obtained at CtripUtil class
 private String hotelName;
 private String hotelEname;
 private String cityName;
 private String provinceName;
 private String address;
 private double lat;
 private double lng;
 private String coordinates;
 private String tel;
 private int hotelStars;


 public String getUrlSource(String url) throws IOException {
  URL webpage = new URL(url);
  URLConnection yc = webpage.openConnection();
  BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
  String inputLine;
  StringBuilder a = new StringBuilder();
  while ((inputLine = in.readLine()) != null)
   a.append(inputLine);
  in.close();

  return a.toString();
 }

 public void setAll(String str) throws Exception {
  try {
   setHotelName(str);
   setHotelEname(str);
   setCityName(str);
   setProvinceName(str);
   setAddress(str);
   setLat(str);
   setLng(str);
   setCoordinates();
   setTel(str);
   setHotelStars(str);
  } catch (Exception e) {
   throw new Exception(e);
  }

 }

 public String setHotelName(String str) throws Exception {
  try {
   int index = str.indexOf("cn_n");
   hotelName = str.substring(str.indexOf(">", index) + 1, str.indexOf("<", index));
  } catch (Exception e) {
   throw new Exception(e);
  }
  return hotelName;

 }

 public String setHotelEname(String str) throws Exception {
  try {
   int index = str.indexOf("en_n");
   if (index == -1) {
    return hotelEname;
   }
   hotelEname = str.substring(str.indexOf(">", index) + 1, str.indexOf("<", index));
  } catch (Exception e) {
   throw new Exception(e);
  }
  return hotelEname;
 }

 public String setCityName(String str) {
  int index = str.indexOf("city");
  cityName = str.substring(index + 5, str.indexOf(">", index) - 1);
  return cityName;
 }

 public String setProvinceName(String str) {
  int index = str.indexOf("province");
  provinceName = str.substring(index + 9, str.indexOf(";", index));
  return provinceName;
 }
 
 public String setAddress(String str) {
  int index = str.lastIndexOf("酒店地址");
  if (!provinceName.equals(cityName)) {
   address = provinceName + cityName;
  } else {
   address = provinceName;
  }

  address += str.substring(index + 5, str.indexOf(";", index));
  return address;
 }

 public double setLat(String str) {
  int index = str.indexOf("latitude");
  lat = Double.parseDouble(str.substring(str.indexOf("content=", index) + 9, str.indexOf("/>", index) - 2));
  return lat;
 }

 public double setLng(String str) {
  int index = str.indexOf("longitude");
  lng = Double.parseDouble(str.substring(str.indexOf("content=", index) + 9, str.indexOf("/>", index) - 2));
  return lng;
 }

 public String setCoordinates() {
  coordinates = "" + lat + ", " + lng;
  return coordinates;
 }

 public String setTel(String str) throws Exception {
  try {
   int index = str.indexOf("电话0");
   if (index == -1) {
    return tel;
   }
   tel = str.substring(index + 2, index + 14);
  } catch (Exception e) {
   throw new Exception(e);
  }
  return tel;
 }

 public int setHotelStars(String str) throws Exception {
  try {
   int index = str.indexOf("hotel_stars");
   if (index == -1) {
    return hotelStars;
   }
   hotelStars = Integer.parseInt(str.substring(index + 11, index + 13));
  } catch (Exception e) {
   throw new Exception(e);
  }

  return hotelStars;
 }

 public String getHotelName() {
  return hotelName;
 }

 public String getHotelEname() {
  return hotelEname;
 }

 public String getCityName() {
  return cityName;
 }

 public String getProvinceName() {
  return provinceName;
 }

 public String getAddress() {
  return address;
 }

 public double getLat() {
  return lat;
 }

 public double getLng() {
  return lng;
 }

 public String getCoordinates() {
  return coordinates;
 }

 public String getTel() {
  return tel;
 }

 public int getHotelStars() {
  return hotelStars;
 }


 /**
  * 
  * @param args
  * 
  * @throws IOException
  */
 public static void main(final String args[]) throws IOException
   {

  final List<String> list = new ArrayList<String>();
  list.add("http://hotels.ctrip.com/hotel/427952.html");
  list.add("http://hotels.ctrip.com/hotel/671.html");
  list.add("http://hotels.ctrip.com/hotel/2005959.html");
  list.add("http://hotels.ctrip.com/hotel/481810.html");
  list.add("http://hotels.ctrip.com/hotel/2104633.html");
  list.add("http://hotels.ctrip.com/hotel/1481502.html");
  list.add("http://hotels.ctrip.com/hotel/1720124.html");
  list.add("http://hotels.ctrip.com/hotel/2165407.html");
  list.add("http://hotels.ctrip.com/hotel/1636803.html");
  list.add("http://hotels.ctrip.com/hotel/371188.html");


    final WebContent wc = new WebContent();

  for (int i = 0; i < list.size(); i++) {
   String webinfo = wc.getUrlSource(list.get(i));
   if (webinfo == null || webinfo.length() == 0 || webinfo.indexOf("验证") != -1) {
    continue;
   }
   try {
    wc.setAll(webinfo);
   } catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
   }
   System.out.println(wc.getHotelName());
   System.out.println(wc.getHotelEname());
   System.out.println(wc.getCityName());
   System.out.println(wc.getProvinceName());
   System.out.println(wc.getAddress());
   System.out.println(wc.getLat());
   System.out.println(wc.getLng());
   System.out.println(wc.getCoordinates());
   System.out.println(wc.getTel());
   System.out.println(wc.getHotelStars());
  }
   }
}