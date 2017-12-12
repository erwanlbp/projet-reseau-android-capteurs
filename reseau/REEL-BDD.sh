#!/bin/bash
# Vers backbone
ifconfig eth51 192.168.5.1/24

route add default gw 192.168.5.1 eth51
