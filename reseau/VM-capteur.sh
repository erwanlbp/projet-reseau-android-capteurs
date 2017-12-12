#!/bin/bash
# Vers Backbone
ifconfig eth11 192.168.1.1/24

route add default gw 192.168.1.1 eth11
