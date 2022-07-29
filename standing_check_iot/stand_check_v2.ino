#include <SoftwareSerial.h> 

//stand-check fields
int trigger_pin = 6;
int echo_pin = 7;
int buzzer_pin = 10; 
int time;
int distance; 
unsigned long StartTime;
unsigned long CurrentTime;
unsigned long ElapsedTime;
bool mode_check=false;
String sitting_period;

//// Wi-Fi fields
String ag_ismi = "";                   //Ağımızın adını buraya yazıyoruz.    
String ag_sifresi = "";             //Ağımızın şifresini buraya yazıyoruz.

int rxPin = 2;                                               //ESP8266 RX pini
int txPin = 3;                                               //ESP8266 TX pini
SoftwareSerial esp(rxPin, txPin);                             //Seri haberleşme pin ayarlarını yapıyoruz.

//ThingSpeak fields
String ip = "184.106.153.149";                                //Thingspeak ip address


void setup() {
  
  //stand-check settings
  pinMode (trigger_pin, OUTPUT); 
  pinMode (echo_pin, INPUT);
  pinMode (buzzer_pin, OUTPUT);

  //wi-fi settings
  Serial.begin(9600);  //Seri port ile haberleşmemizi başlatıyoruz.
  Serial.println("Started");
  esp.begin(115200);                                          //ESP8266 ile seri haberleşmeyi başlatıyoruz.
  esp.println("AT");                                          //AT komutu ile modül kontrolünü yapıyoruz.
  Serial.println("AT Yollandı");
  while(!esp.find("OK")){                                     //Modül hazır olana kadar bekliyoruz.
    esp.println("AT");
    Serial.println("ESP8266 Bulunamadı.");
  }
  Serial.println("OK Komutu Alındı");
  
//  esp.println("AT+RST");                                     //ESP8266'yı resetliyoruz.
//  delay(1000);
//  while(!esp.find("ready"))    
  
  esp.println("AT+CWMODE=1");                                 //ESP8266 modülünü client olarak ayarlıyoruz.
  while(!esp.find("OK")){                                     //Ayar yapılana kadar bekliyoruz.
    esp.println("AT+CWMODE=1");
    Serial.println("Ayar Yapılıyor....");
  }
  Serial.println("Client olarak ayarlandı");
  Serial.println("Aga Baglaniliyor...");
  esp.println("AT+CWJAP=\""+ag_ismi+"\",\""+ag_sifresi+"\"");    //Ağımıza bağlanıyoruz.
  while(!esp.find("OK"));                                     //Ağa bağlanana kadar bekliyoruz.
  Serial.println("Aga Baglandi.");
  delay(1000);
  
  digitalWrite (buzzer_pin, HIGH);
  delay (500);
  digitalWrite (buzzer_pin, LOW);
}

void loop() {
  // put your main code here, to run repeatedly:
  stand_check();
}

void stand_check(){
  digitalWrite (trigger_pin, HIGH);
  delayMicroseconds (10);
  digitalWrite (trigger_pin, LOW);
  time = pulseIn (echo_pin, HIGH);
  distance = (time * 0.034) / 2;
  
  if (distance <= 85) 
  {
    mode_check=true;
    CurrentTime = millis();
    ElapsedTime = CurrentTime - StartTime;
    Serial.println (" Sitting Started ");
    Serial.print (" Distance= ");              
    Serial.println (distance);        
    Serial.print (" Elapsed Time= ");              
    Serial.println (ElapsedTime);

    if(ElapsedTime > 10000)
    {
      digitalWrite (buzzer_pin, HIGH);
    }
    delay (500);
  }
  
  else {
    digitalWrite (buzzer_pin, LOW);  
    if(mode_check == true){
      Serial.println ("Total Sitting Time= ");  
      Serial.println(ElapsedTime);
      http_request(String(ElapsedTime));
      mode_check = false;
    }
    StartTime = millis();
    Serial.println (" Sitting ended ");    
    delay (500);
  }
}

void http_request(String field1){
  esp.println("AT+CIPSTART=\"TCP\",\""+ip+"\",80");           //Thingspeak'e bağlanıyoruz.
  if(esp.find("Error")){                                      //Bağlantı hatası kontrolü yapıyoruz.
    Serial.println("AT+CIPSTART Error");
  }
  sitting_period = field1;
  String veri = "GET https://api.thingspeak.com/update?api_key=8V1FT1UALIVVLJOU";   //Thingspeak komutu. Key kısmına kendi api keyimizi yazıyoruz.                                   //Göndereceğimiz sıcaklık değişkeni
  veri += "&field1=";
  veri += String(sitting_period);                                       //Göndereceğimiz nem değişkeni
  veri += "\r\n\r\n"; 
  esp.print("AT+CIPSEND=");                                   //ESP'ye göndereceğimiz veri uzunluğunu veriyoruz.
  esp.println(veri.length()+2);
  delay(2000);
  if(esp.find(">")){                                          //ESP8266 hazır olduğunda içindeki komutlar çalışıyor.
    esp.print(veri);                                          //Veriyi gönderiyoruz.
    Serial.println(veri);
    Serial.println("Veri gonderildi.");
    delay(1000);
  }
  Serial.println("Baglantı Kapatildi.");
  esp.println("AT+CIPCLOSE");                                //Bağlantıyı kapatıyoruz
  delay(1000);                                               //Yeni veri gönderimi için 1 dakika bekliyoruz.
}
