package com.google.com;
import java.io.PrintWriter;
public class my_main {
    public static void main(String[] args) {
        String adapter_name="Wi-fi";
        /* check your adapter name and change it */
        String ip_address="192.168.100.55";
        /* put ip as you want */
        String subnet_mask="255.255.255.0";
        String default_gateway="192.168.101.1";
        String dns_1="8.8.8.8";
        String dns_2="8.8.4.4";
        String[] command =
                {
                        "cmd",
                };
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
            new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
            PrintWriter stdin = new PrintWriter(p.getOutputStream());
            stdin.println("netsh int ip set address "+adapter_name+" static "+ip_address+" "+subnet_mask+" "+default_gateway);
            stdin.println("netsh int ip set dns "+adapter_name+" static "+dns_1+" primary");
            stdin.println("netsh interface ip add dns "+adapter_name+" "+dns_2+" INDEX=2");
            stdin.close();
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
/* always run as administrator mode for proper run*/
