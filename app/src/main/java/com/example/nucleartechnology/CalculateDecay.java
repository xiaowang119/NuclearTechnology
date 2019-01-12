package com.example.nucleartechnology;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shinecore
 */
public class CalculateDecay 
{
    public double CalculateActive(double activity, double landa, double losttime)
    {
        double temp = activity*Math.exp(-landa*losttime);
        return temp;
    }
    
    public double CalculateOriginActive(double activity, double landa, double losttime)
    {
        double temp = activity*Math.exp(landa*losttime);
        return temp;
    }
    
    public double CalculateMassActive(double mass, double landa, double AMass)
    {
        double temp;
        temp = mass/AMass*6.023E23*landa;
        return temp;
    }
    
    
}
