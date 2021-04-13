package com.company;
import purejavacomm.*;


import java.awt.*;
import java.io.*;
import java.util.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Main {
    public static Logger logger = Logger.getLogger(Main.class.getName());
    public static List<CommPortIdentifier> serialPorts = new ArrayList<CommPortIdentifier>();
    public static BufferedReader bufferedReader;
    public static Thread readThread;
    public static Robot robot;
    public static CommPort commPort;
    public static int[] resi, cali = {0, 0};

    public static void main(String[] args) throws IOException, AWTException {
        updateSerialPorts();
        SerialSetup();
        updateResi();
        cleanUp();
    }

    public static void cleanUp() {
        logger.log(Level.INFO, "Shutting Down Gracefully");
        logger.log(Level.INFO, "Closing CommPort");
        commPort.close();
        logger.log(Level.INFO, "CommPort Closed");
        logger.log(Level.INFO, "Closing Buffered Reader");
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.log(Level.INFO, "Buffered Reader closed");

    }

    public static void SerialSetup() throws IOException {
        commPort = null;
        System.out.println(serialPorts.get(1).getName());
        try {
            logger.log(Level.INFO, "Attempting to open " + serialPorts.get(0).getName());
            commPort = serialPorts.get(0).open("ArduinoSerialController", 400);
        } catch (PortInUseException e) {
            logger.log(Level.SEVERE, "PORT IN USE, this could be due to insufficient permissions");
            e.printStackTrace();
            return;
        }
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(commPort.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
    public static void updateSerialPorts() {
        serialPorts.clear();
        Enumeration<CommPortIdentifier> ports = CommPortIdentifier.getPortIdentifiers();
        while (ports.hasMoreElements()) {
            serialPorts.add(ports.nextElement());
            System.out.println(serialPorts.get(serialPorts.size() - 1).getName() + ":" + serialPorts.get(serialPorts.size() - 1).isCurrentlyOwned());
        }
        logger.log(Level.INFO, "Total Available Comm Ports: " + serialPorts.size());
    }
    public static void calibrate() {
        for(int i = 2000; i >= 0; i++) {

        }
    }
    public static void updateResi()
    {
        List<int[]> integers = new ArrayList<>();
        try {
            for(int i = 5; i>=0; i--) {
                integers.add(Arrays.stream(bufferedReader.readLine().split(":")).mapToInt(Integer::parseInt).toArray());
            }

            //System.out.println(Arrays.toString(integers.stream().sorted().collect(Collectors.toList()).get(3)));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void mouseUpdate() {

    }
}
    //Users need to be in 'lock' and 'uucp' groups to connect to devices