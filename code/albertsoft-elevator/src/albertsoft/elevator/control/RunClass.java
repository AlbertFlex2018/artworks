/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package albertsoft.elevator.control;

import albertsoft.elevator.control.cmd.AdminCmd;
import albertsoft.elevator.control.cmd.BuildingCmd;
import albertsoft.elevator.control.cmd.ElevatorCmd;
import albertsoft.elevator.control.cmd.SystemCmd;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Administrator
 */
public class RunClass {
    public static void main(String[] args) {
        Map<String,CmdInterface> interfaces=new HashMap<>();
        putInterface(interfaces,new AdminCmd());
        putInterface(interfaces,new BuildingCmd());
        putInterface(interfaces,new ElevatorCmd());
        putInterface(interfaces,new SystemCmd());

        Scanner scan=new Scanner(System.in);
        while(true){
            String cmd=scan.nextLine();
            String[] cmds=cmd.split(" ");

            CmdInterface in=interfaces.get(cmds[0]);

            if(in==null){
                System.err.println("no interface for cmd:"+cmd);
                continue;
            }

            System.out.println(in.cmd(cmds));
        }
    }
    private static void putInterface(Map<String,CmdInterface> ins,CmdInterface in){
        ins.put(in.getName(), in);
    }
}
