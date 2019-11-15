package com.appleiapservices.demo.util;

public class LoadTenonLib {
    public native LoadTenonLib initP2PNetwork(String ip, int port, String bootstrap, String file_path, String pri_key);
    static{
        System.loadLibrary("libclient");
    }
    public native void p2pDestroy();
    static{

    }
    public native void getRouteNodes(String country);
    static{

    }

    public native void getVpnNodes(String country);
    static{

    }
    public native void getPublicKey();
    static{

    }
    public native void getP2PSocket();
    static{

    }

    public native void isFirstTimeInstall();
    static{

    }
    public native void getTransactions();
    static{

    }
    public native float getRandom();
    static{
        System.loadLibrary("libTenonTest");
    }
    public native float tenonTest();
    static{
        System.loadLibrary("libTenonTest");
    }
    public native float getBalance();
    static{
        System.loadLibrary("libTenonTest");
    }
    public native void setFirstTimeInstall();
    static{

    }
    public native void vpnNodeHeartbeat(String dht_key);
    static{

    }

    public native void createAccount(String dht_key);
    static{

    }

    public native void transaction(String to,int amount);
    static{

    }

    public native void getTransaction(String tx_gid);
    static{

    }

    public native void resetTransport(String local_ip,int local_port);
    static{

    }

    public native void vpnLogin(String server_id);
    static{

    }

    public native void checkVersion();
    static{

    }

    public native void payforVpn(String account,long tenon,String gid);
    static{

    }

    public native void checkVip();
    static{

    }

    public native void checkFreeBandwidth();
    static{

    }
}
