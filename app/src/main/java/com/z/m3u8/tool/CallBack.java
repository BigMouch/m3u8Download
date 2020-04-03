package com.z.m3u8.tool;

public class  CallBack{
    private CallBack(){

    }
    private static  volatile  CallBack callBack;
    public  static synchronized  CallBack get(){
        if(callBack==null){
            callBack=new CallBack();
        }
        return  callBack;
    }

    private IPlayUrl iPlayUrl;
    private String url;

    public  void setPlayListen(IPlayUrl iPlayUrl){
        this.iPlayUrl=iPlayUrl;
    }
    public  void setUrl(String url){
        this.url=url;
    }
    public  void callUrl(){
        if(iPlayUrl!=null&&!url.isEmpty()){
            iPlayUrl.callUrl(url);
            url="";
        }
    }
    public  void callUrl(String url){
        if(iPlayUrl!=null){
            iPlayUrl.callUrl(url);
        }

    }
}
