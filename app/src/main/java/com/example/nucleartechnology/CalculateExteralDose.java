package com.example.nucleartechnology;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shinecore
 */

import java.util.ArrayList;
import java.util.List;

public class CalculateExteralDose 
{
    private CalculateCoef m_coef ;
    private List<Material> m_air;
    private List<Material> m_body;
    public CalculateExteralDose(String fileName)
    {
        m_coef = new CalculateCoef();
        m_coef.Initial(fileName);
        
        // 创建空气
        m_air = new ArrayList<Material>();
        Material mat = new Material(7,"N",0.78);
        m_air.add(mat);
        Material mat1 = new Material(8,"O",0.21);
        m_air.add(mat1);
        Material mat2 = new Material(18,"Ar",0.01);
        m_air.add(mat2);
    }
    public double AirAbsDose( List<Double> LstEg, List<Double> LstPb,double distance, double active)
    {
        double dose=0;
        int i = 0;
        for(double eg: LstEg)
        {            
            dose = dose + AirAbsDoseConstant(eg, LstPb.get(i));
            i++;
        }
        dose = active*dose/(Math.PI*4*distance*distance);
        return dose;
    }
    
    public double AirAbsDose(double eg, double pb, double distance, double active)
    {
        double dose;
        dose = AirAbsDoseConstant(eg,pb);
        dose = active*dose/(Math.PI*4*distance*distance);
        return dose;
    }
    
    private double AirAbsDoseConstant(double eg, double pb)
    {
        double dose;
        dose = eg*pb*m_coef.CalculateMSEAbCoef(m_air, eg)*1.6E-13;;
        return dose;
    }
    
    // 计算屏蔽后的空气吸收剂量
    public double AirAbsDose( List<Double> LstEg, List<Double> LstPb,double distance, double active, List<Material> shieldMat, double density, double thickness)
    {
        double dose=0;
        int i = 0;
        for(double eg: LstEg)
        {            
            dose = dose + AirAbsDose(eg, LstPb.get(i), shieldMat, density, thickness);
            i++;
        }
        dose = active*dose/(Math.PI*4*distance*distance);
        return dose;       
    }
    public double AirAbsDose(double eg, double pb, double distance, double active, List<Material> shieldMat, double density, double thickness)
    {
        double dose;
        dose = AirAbsDose(eg,pb,shieldMat, density, thickness);
        dose = active*dose/(Math.PI*4*distance*distance);
        return dose;
    }    
    private double AirAbsDose(double eg, double pb,  List<Material> shieldMat, double density, double thickness)
    {
        //线衰减系�?
        double attCS = m_coef.CalculateMSEAttCoef(shieldMat, eg, density);
        // 计算屏蔽后的粒子通量
       double pb1 = pb * Math.exp(-attCS*thickness);
        
        double dose;
        dose = eg*pb1*m_coef.CalculateMSEAbCoef(m_air, eg)*1.6E-13;
        return dose;
    } 
    
    // 其它物质的吸收剂�?
    public double MatAbsDose( List<Double> LstEg, List<Double> LstPb,double distance, double active, List<Material> lstMat)
    {
        double dose=0;
        int i = 0;
        for(double eg: LstEg)
        {            
            dose = dose + MatAbsDose(eg, LstPb.get(i), lstMat);
            i++;
        }
        dose = active*dose/(Math.PI*4*distance*distance);
        return dose;       
    }
    public double MatAbsDose(double eg, double pb, double distance, double active,List<Material> lstMat)
    {
        double dose;
        dose = MatAbsDose(eg,pb,lstMat);
        dose = active*dose/(Math.PI*4*distance*distance);
        return dose;
    }    
    private double MatAbsDose(double eg, double pb, List<Material> lstMat)
    {
        double dose;
        dose = eg*pb*m_coef.CalculateMSEAbCoef(lstMat, eg)*1.6E-13;
        return dose;
    }
    
    // 其它物质屏蔽后的吸收剂量
    public double MatAbsDose( List<Double> LstEg, List<Double> LstPb,double distance, double active, List<Material> lstMat, List<Material> shieldMat, double density, double thickness)
    {
        double dose=0;
        int i = 0;
        for(double eg: LstEg)
        {            
            dose = dose + MatAbsDose(eg, LstPb.get(i), lstMat, shieldMat, density, thickness);
            i++;
        }
        dose = active*dose/(Math.PI*4*distance*distance);
        return dose;       
    }
    public double MatAbsDose(double eg, double pb, double distance, double active,List<Material> lstMat, List<Material> shieldMat, double density, double thickness)
    {
        double dose;
        dose = MatAbsDose(eg,pb,lstMat,shieldMat, density, thickness);
        dose = active*dose/(Math.PI*4*distance*distance);
        return dose;
    }    
    private double MatAbsDose(double eg, double pb, List<Material> lstMat, List<Material> shieldMat, double density, double thickness)
    {
        // 线衰减系�?
        double attCS = m_coef.CalculateMSEAttCoef(shieldMat, eg, density);
        // 计算屏蔽后的粒子通量
        pb = pb * Math.exp(attCS*thickness);
        
        double dose;
        dose = eg*pb*m_coef.CalculateMSEAbCoef(lstMat, eg)*1.6E-13;
        return dose;
    } 
    
    // 根据活度，吸收剂量，求解无屏蔽下的源的距�?
    public double CalculateAirDistance(double eg, double pb,double activity, double dose )
    {
        double tempDose;
       tempDose = AirAbsDoseConstant(eg,pb);
       double distance;
       if(tempDose<=0)
           return -1;
       distance = activity*tempDose/(dose*Math.PI*4);
       if(distance<=0)
           return -1;
       distance = Math.sqrt(distance);
       return distance;
    }
    
    public double CalculateAirDistance(List<Double> LstEg, List<Double> LstPb,double activity, double dose )
    {
        double tempDose;
        int i = 0;
        tempDose = 0.0;
        for(double eg: LstEg)
        {            
            tempDose = tempDose + AirAbsDoseConstant( eg, LstPb.get(i) );
            i++;
        }
       double distance;
       if(tempDose<=0)
           return -1;
       distance = activity*tempDose/(dose*Math.PI*4);
       if(distance<=0)
           return -1;
       distance = Math.sqrt(distance);
       return distance;
    }    
    // 根据活度，吸收剂量，求解任意物质下无屏蔽状�?下的源的距离
     public double CalculateAirDistance(double eg, double pb,double activity, double dose,List<Material> absMat )
    {
        double tempDose;
       tempDose = MatAbsDose(eg,pb,absMat);
       double distance;
       if(tempDose<=0)
           return -1;
       distance = activity*tempDose/(dose*Math.PI*4);
       if(distance<=0)
           return -1;
       distance = Math.sqrt(distance);
       return distance;
    } 
    
    public double CalculateAirDistance(List<Double> LstEg, List<Double> LstPb,double activity, double dose,List<Material> absMat )
    {
        double tempDose;
        int i = 0;
        tempDose = 0.0;
        for(double eg: LstEg)
        {            
            tempDose = tempDose + MatAbsDose( eg, LstPb.get(i),absMat );
            i++;
        }
       double distance;
       if(tempDose<=0)
           return -1;
       distance = activity*tempDose/(dose*Math.PI*4);
       if(distance<=0)
           return -1;
       distance = Math.sqrt(distance);
       return distance;
    }
    // 求解屏蔽体厚�?
    public double CalculateShieldThick(List<Double> LstEg, List<Double> LstPb,double distance, double activity, double dose,List<Material> lstShield, double density)
    {
        double tempDose;
        int i = 0;
        tempDose = 0.0;
        double maxEg=LstEg.get(0),minEg=LstEg.get(0);
        for(double eg: LstEg)
        {            
            tempDose = tempDose + AirAbsDoseConstant( eg, LstPb.get(i) );
            if(eg>maxEg)
                maxEg = eg;
            if(eg<minEg)
                minEg = eg;
            i++;
        }
        tempDose = tempDose*activity/(4*Math.PI*distance*distance);

        double maxThick,minThick;
        double curThick;
        // 得到最大能量的衰减系数
        double maxEgCoef,minEgCoef;
        maxEgCoef = m_coef.CalculateMSEAttCoef(lstShield, maxEg, density);
        minEgCoef = m_coef.CalculateMSEAttCoef(lstShield, minEg, density);
        if(tempDose<=0)
            return -1;
        // 计算最大厚度和最小厚度
        maxThick = -Math.log((dose/tempDose))/maxEgCoef;
        minThick = -Math.log((dose/tempDose))/minEgCoef;
        int j=0;
        while(j<=10)
        {
            curThick = (maxThick+minThick)/2.0;
            tempDose = AirAbsDose( LstEg, LstPb,distance, activity, lstShield, density, curThick);
            if(tempDose>dose)
                minThick = curThick;
            else
                maxThick = curThick;
            if(tempDose==dose)
                break;
            j++;
        }
        return curThick = (maxThick+minThick)/2.0;
    }
    // 求解源的活度
    public double CalculateActive(List<Double> LstEg, List<Double> LstPb,double distance, double dose)
    {
        double tempDose;
        int i = 0;
        tempDose = 0.0;
        for(double eg: LstEg)
        {            
            tempDose = tempDose + AirAbsDoseConstant( eg, LstPb.get(i) );
            i++;
        }

       double activity;
       if(tempDose<=0)
           return -1;
       activity = dose/tempDose*(4*Math.PI*distance*distance);
       if(activity<=0)
           return -1;
       return activity;        
    }
}
