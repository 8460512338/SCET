package com.example.scet;

import java.io.Serializable;
import java.util.HashMap;

public class PackModel implements Serializable {
    public String gcapacity,gdate,ghour,gloc,gname,gpic,gprice,gscore,gtime,gdesc;

    public PackModel(String gcapacity, String gdate, String ghour, String gloc, String gname, String gpic, String gprice, String gscore, String gtime,String gdesc) {
        this.gcapacity = gcapacity;
        this.gdate = gdate;
        this.ghour = ghour;
        this.gloc = gloc;
        this.gname = gname;
        this.gpic = gpic;
        this.gprice = gprice;
        this.gscore = gscore;
        this.gtime = gtime;
        this.gdesc=gdesc;
    }

    public PackModel() {

    }

    public String getGcapacity() {
        return gcapacity;
    }

    public void setGcapacity(String gcapacity) {
        this.gcapacity = gcapacity;
    }

    public String getGdate() {
        return gdate;
    }

    public void setGdate(String gdate) {
        this.gdate = gdate;
    }

    public String getGhour() {
        return ghour;
    }

    public void setGhour(String ghour) {
        this.ghour = ghour;
    }

    public String getGloc() {
        return gloc;
    }

    public void setGloc(String gloc) {
        this.gloc = gloc;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getGpic() {
        return gpic;
    }

    public void setGpic(String gpic) {
        this.gpic = gpic;
    }

    public String getGprice() {
        return gprice;
    }

    public void setGprice(String gprice) {
        this.gprice = gprice;
    }

    public String getGscore() {
        return gscore;
    }

    public void setGscore(String gscore) {
        this.gscore = gscore;
    }

    public String getGtime() {
        return gtime;
    }

    public void setGtime(String gtime) {
        this.gtime = gtime;
    }
    public String getGdesc() {
        return gdesc;
    }


    public  static  HashMap<String,Object> convertomap(PackModel model){
        HashMap<String,Object> map=new HashMap<>();
        map.put("gcapacity",model.getGcapacity());
        map.put("gdate",model.getGdate());
        map.put("ghour",model.getGhour());
        map.put("gloc",model.getGloc());
        map.put("gname",model.getGname());
        map.put("gpic",model.getGpic());
        map.put("gprice",model.getGprice());
        map.put("gscore",model.getGscore());
        map.put("gtime",model.getGtime());
        map.put("gdesc",model.getGdesc());
        return map;
    }


}
