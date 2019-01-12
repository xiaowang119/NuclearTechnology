package com.example.nucleartechnology;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shinecore
 */

// 核素参数定义类

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//import java.math;
public class CalculateCoef 
{
    // 公共变量，首先是元素的信息
    List<ElementParam> m_elementCS;
    List<ElementParam> m_elementAb;
    
    public boolean Initial(String fileName)
    {
        // 元素数组初始化
       m_elementCS = new  ArrayList<ElementParam>();
       m_elementAb = new  ArrayList<ElementParam>();
       ElementParam curParam = new ElementParam();
       m_elementCS.add(curParam);
       m_elementAb.add(curParam);
        // 读入数据文件
        // 读入吸收系数文件
        ReadParamFromFile("/data/data/com.example.nucleartechnology/param/attenparam.def",m_elementCS);
        ReadParamFromFile("/data/data/com.example.nucleartechnology/param/absorptparam.def",m_elementAb);
        // 计算吸收限能量
        CalAbLimit(m_elementCS);
        CalAbLimit(m_elementAb);
        return true;
    }
    
    // 计算元素不同壳层的结合能
    private boolean CalAbLimit(List<ElementParam> element)
    {
        double conk = Math.pow(10,-2.2443)/1000;
        double conL1 = Math.pow(10,-3.7155)/1000;
        double conL3 = Math.pow(10,-3.6502)/1000;
        double conM1 = Math.pow(10,-5.0029)/1000;
        double conM5 = Math.pow(10, -5.4086)/1000;
        for(ElementParam curElement: element)
        {
            if(curElement.elemZ>10)
                curElement.kLimit = Math.pow(curElement.elemZ,2.1866)*conk;
            else
                curElement.kLimit = 0.0;
            if(curElement.elemZ>28)
                curElement.L1Limit = Math.pow(curElement.elemZ,2.5691)*conL1;
            else
                curElement.L1Limit = 0.0;
            if(curElement.elemZ>30)
                curElement.L3Limit = Math.pow(curElement.elemZ,2.4932)*conL3;
            else
               curElement.L3Limit = 0.0; 
            if(curElement.elemZ>51)
                curElement.M1Limit = Math.pow(curElement.elemZ,2.9212)*conM1;
            else
                curElement.M1Limit = 0.0;
            if(curElement.elemZ>60)
                curElement.M5Limit = Math.pow(curElement.elemZ, 3.0329)*conM5;
            else
                curElement.M5Limit = 0.0;
        }
        return true;
    }
    // calculate the hydry compton scatter cross section
    private double CalculateHScatterCS(double energy)
    {
        if(energy<=0)
            return 0.0;
        double cs = 0.0;
        double lgEg = Math.log10(energy);
        cs = -0.1124*lgEg*lgEg-0.4707*lgEg-0.8983;
        cs = Math.pow(10,cs);
        return cs;
    }
    
        // calculate the hydry compton scatter absorption cross section
    private double CalculateHScatterAbCS(double energy)
    {
        if(energy<=0)
            return 0.0;
        double cs = 0.0;
        double lgEg = Math.log10(energy);
        cs = -0.2723*lgEg*lgEg-0.1144*lgEg-1.2532;
        cs = Math.pow(10,cs);
        return cs;
    }
    // 散射截面，单位cm2/g
    public double ScatterCS(ElementParam curElement, double energy)
    {
        double HCS = CalculateHScatterCS(energy);
        double cs = HCS*curElement.elemZ/curElement.elemMA;
        return cs;
    }
    // 散射吸收截面， 单位cm2/g
    public double ScatterAbCS(ElementParam curElement, double energy)
    {
        double HCS = CalculateHScatterAbCS(energy);
        double cs = HCS*curElement.elemZ/curElement.elemMA;
        return cs;    
    }
    // 光电效应截面，单位cm2/g
    public double PhotoelectricCS(ElementParam curElement, double energy)
    {
        if(energy<=0)
            return 0;
        double cs = 0.0;
        //cs = curElement.a*Math.pow(energy, curElement.b);
        //cs = cs - curElement.a*Math.pow(curElement.kLimit,curElement.b);
        //cs = cs - curElement.c * Math.pow(10,(curElement.d*(energy-curElement.kLimit)));
        if(curElement.kLimit!=0)
            cs = curElement.a*Math.pow(curElement.kLimit,curElement.b);
        else
            cs = 0.0;
        if(curElement.d!=0)
            cs = (cs-curElement.c)*Math.pow(10,(curElement.d*(energy-curElement.kLimit)));
        else
            cs = 0.0;
        cs = curElement.a*Math.pow(energy, curElement.b) - cs;
        if((energy<curElement.kLimit))
        {
            cs = curElement.a1*Math.pow(energy,curElement.b1);
        }
        if((energy<curElement.L3Limit) )
        {
            cs = curElement.a2*Math.pow(energy,curElement.b2);
        }
        if(energy<curElement.M5Limit)
        {
            cs = curElement.a3*Math.pow(energy,curElement.b3);
        }
        return cs;
    }
    // 电子对截面，单位cm2/g
    public double PairProCS(ElementParam curElement, double energy)
    {
        if(energy<1.02)
            return 0;
        double cs=0.0;
        cs = curElement.p*energy;
        cs = cs + curElement.q*Math.sqrt(energy);
        cs = cs + curElement.r;
        return cs;
    }
    // 质量能量衰减系数，单位cm2/g
    public double CalculateMSEAttCoef(List<Material> material, double energy)
    {
        if(energy<0)
            return 0;
        double cs = 0.0;
        double totalCS = 0.0;
        for(Material curMat:material)
        {
            cs = 0.0;
            cs = cs + PhotoelectricCS(m_elementCS.get(curMat.elemZ),energy);
            cs = cs + ScatterCS(m_elementCS.get(curMat.elemZ),energy);
            cs = cs + PairProCS(m_elementCS.get(curMat.elemZ),energy);
            totalCS = totalCS + cs*curMat.wg;
        }
        return totalCS;
    }
    // 线衰减系数，单位1/cm;
    public double CalculateMSEAttCoef(List<Material> material, double energy,double density)
    {
        if(energy<0)
            return 0;
        double cs = 0.0;
        double totalCS = 0.0;
        for(Material curMat:material)
        {
            cs = 0.0;
            cs = cs + PhotoelectricCS(m_elementCS.get(curMat.elemZ),energy);
            cs = cs + ScatterCS(m_elementCS.get(curMat.elemZ),energy);
            cs = cs + PairProCS(m_elementCS.get(curMat.elemZ),energy);
            totalCS = totalCS + cs*curMat.wg;
        }
        return totalCS*density;
    }    
    
        // 质量能量吸收系数，单位cm2/g
    public double CalculateMSEAbCoef(List<Material> material, double energy)
    {
        if(energy<0)
            return 0;
        double cs = 0.0;
        double totalCS = 0.0;
        for(Material curMat:material)
        {
            cs = 0.0;
            cs = cs + PhotoelectricCS(m_elementAb.get(curMat.elemZ),energy);
            cs = cs + ScatterAbCS(m_elementAb.get(curMat.elemZ),energy);
            cs = cs + PairProCS(m_elementAb.get(curMat.elemZ),energy);
            totalCS = totalCS + cs*curMat.wg;
        }
        return totalCS;
    }
    // 线吸收系数，单位1/cm;
    public double CalculateMSEAbCoef(List<Material> material, double energy,double density)
    {
        if(energy<0)
            return 0;
        double cs = 0.0;
        double totalCS = 0.0;
        for(Material curMat:material)
        {
            cs = 0.0;
            cs = cs + PhotoelectricCS(m_elementAb.get(curMat.elemZ),energy);
            cs = cs + ScatterAbCS(m_elementAb.get(curMat.elemZ),energy);
            cs = cs + PairProCS(m_elementAb.get(curMat.elemZ),energy);
            totalCS = totalCS + cs*curMat.wg;
        }
        return totalCS*density;
    }
    
    private boolean ReadParamFromFile(String fileName,List<ElementParam> lstElemParam) {
        try {
            String encoding = "GBK";
            File file = new File(fileName);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                
                lineTxt = bufferedReader.readLine();
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    ElementParam curParam = new ElementParam();
                     String[] iArray = lineTxt.split("\\s+");
                     curParam.elemZ = Integer.parseInt(iArray[0] );
                     curParam.name = iArray[1];
                     curParam.elemMA = Double.parseDouble(iArray[2] );
                     curParam.a = Math.pow(10,Double.parseDouble(iArray[3]));
                     curParam.b = Double.parseDouble(iArray[4]);
                     curParam.c = Double.parseDouble(iArray[5]);
                     curParam.d = Double.parseDouble(iArray[6]);
                     curParam.a1 = Math.pow(10,Double.parseDouble(iArray[7]));
                     curParam.b1 = Double.parseDouble(iArray[8]);
                     curParam.a2 = Math.pow(10,Double.parseDouble(iArray[9]));
                     curParam.b2 = Double.parseDouble(iArray[10]);
                     curParam.a3 = Math.pow(10,Double.parseDouble(iArray[11]));
                     curParam.b3 = Double.parseDouble(iArray[12]);
                     curParam.p = Double.parseDouble(iArray[13])*1.0e-6;
                     curParam.q = Double.parseDouble(iArray[14])*1.0e-6;
                     curParam.r = Double.parseDouble(iArray[15])*1.0e-6;   
                     
                     lstElemParam.add(curParam);
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return true;
    }
}
