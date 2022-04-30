package org.maria.enroll_me.push;

public class Send {
    public   String to;
    public   Notification notification = new Notification();
    public   Data data = new Data();
    public class Notification{
       public     String body;
       public      String title;
       public     String icon;
    }

    public  class Data{
         public    String ip;
         public   String name;
        public int type;
        public int meeting ;
    }
}
