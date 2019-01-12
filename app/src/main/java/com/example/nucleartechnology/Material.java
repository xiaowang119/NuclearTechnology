package com.example.nucleartechnology;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shinecore
 */
public class Material
{
    public int elemZ;
    public String name;
    public double wg;
    public Material(int z, String n, double w)
    {
        elemZ = z;
        name = n;
        wg = w;
    }
}

