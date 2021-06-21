/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package golfbooking;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Kill_App {

private static final String TASKLIST = "tasklist";
private static final String KILL = "taskkill /IM ";

public static boolean isProcessRunging(String serviceName) throws Exception {

Process p = Runtime.getRuntime().exec(TASKLIST);
BufferedReader reader = new BufferedReader(new InputStreamReader(
p.getInputStream()));
String line;

while ((line = reader.readLine()) != null) {

//System.out.println(line);
if (line.contains(serviceName)) {
return true;
}
}
return false;
}

public static void killProcess(String serviceName) throws Exception {

Runtime.getRuntime().exec(KILL + serviceName);

}


public static void killApp(String processName) throws Exception {
 
System.out.print(isProcessRunging(processName));
int counter = 0;
while (isProcessRunging(processName) && counter < 30) {
counter++;
killProcess(processName);
}
}
}