Pour générer le jar executable :
- 

1) Edit configuration

2) Ajouter : Run Maven Goal : assembly:single

3) Jar dans le dossier /target à la racine

Pour lancer le jar
- 

java -jar GenFlux.jar \<ipFirebase> \<port envoi> \<port ecoute> \<mode>

**Ip :**
 
Par défaut 127.0.0.1

**Port Envoi**

Pour sur lequel les flux sont envoyés

**Port Ecoute**

Pour sur lequel les flux et les commandes de start / stop sont réceptionnées

**Modes :**
- gen-capteur : Générer les flux et écouter les commandes de start et stop
- capteurs : Ecoute les flux et persiste dans firebase
- stop : Envoyer commande de stop du LightCapteur


