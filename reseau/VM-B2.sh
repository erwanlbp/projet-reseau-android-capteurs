#!/bin/bash
# Vers backbone vers capteurs
ifconfig eth320 192.168.3.20/24
 
# Vers backbone vers bdd
ifconfig eth410 192.168.4.10/24

# Pour en faire un routeur
echo 1 > /proc/sys/net/ipv4/ip_forward

/etc/init.d/zebra start
