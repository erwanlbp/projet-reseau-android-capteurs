# Projet-reseau-android-capteurs

## Reseau

### Conventions
* Les routeurs ont des adresse IP en dizaine (ex: 192.168.1.20)
* (Si besoin) la première interface des routeurs est vers l'extérieur du réseau
* Les routeurs sont nommés R puis un chiffre et ce qu'ils connectent
* Pour Netkit, les réseaux en extérieurs commencent par E et les intérieurs par I

### Lancement

Nous considèrerons que la variable `$PATH_RESEAU` pointe vers le dossier `reseau/` situé à la racine du projet Git.

Pour lancer le lab Netkit
```bash
lstart -d $PATH_RESEAU
```

Pour arreter le lab Netkit
```bash
lcrash -d $PATH_RESEAU
```

## Application android

## Réseau de capteurs