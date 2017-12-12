#!/bin/bash
# Vers Capteurs
ifconfig eth110 192.168.1.10/24

# Vers l'internet
ifconfig eth210 192.168.2.10/24

# Vers Backbone intérieur vers bdd
ifconfig eth310 192.168.3.10/24

# Pour en faire un routeur
sudo echo 1 > /proc/sys/net/ipv4/ip_forward

# Pour avoir les DNS (pour apt-get)
sudo echo "nameserver 8.8.8.8" >> /etc/resolv.conf

/etc/init.d/zebra start
