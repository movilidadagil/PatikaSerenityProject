#Serenity Project Serenity BDD is an open source library that aims to make the idea of living documentation a reality.

Serenity projesi Serenity BDD dökümantasyon fikrini hayata geçirmeyi gerçeğe dönüştürmeyi amaçlayan açık kaynak bir kütüphanedir.


###PatikaSerenityProject adında projemizi oluşturmak için adımlar
1.PatikaSerenityProject adında bir maven projesi oluşturun

2. Pom.xml altına aşağıdaki properties sectionını yapıştırın.

<properties>
<maven.compiler.source>8</maven.compiler.source>
<maven.compiler.target>8</maven.compiler.target>
<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>

3.Aşağıdaki dependency leri dependencies section ı altına ekleyin
<dependencies>
buraya dependency ler gelir.
</dependencies>

<!--       Serenity win ilk eklenecek temel dependency si. -->
        <dependency>
            <groupId>net.serenity-bdd</groupId>
            <artifactId>serenity-core</artifactId>
            <version>2.4.4</version>
        </dependency>
<!--       Rest assured ile serenity i birlikte kullanacağımız dependencyler -->
        <dependency>
            <groupId>net.serenity-bdd</groupId>
            <artifactId>serenity-rest-assured</artifactId>
            <version>2.4.4</version>
        </dependency>

        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.7.1</version>
            <scope>test</scope>
        </dependency>
        <!-- serenity için Junit 5 desteği -->
        <dependency>
            <groupId>io.github.fabianlinz</groupId>
            <artifactId>serenity-junit5</artifactId>
            <version>1.6.0</version>
        </dependency>
Ekleme yaptıktan sonra pom.xml inize sağ tuş yapıp maven reload projects deyin.


4.Build plugin ini ekleyin.

  <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>8</source>
                    <target>8</target>
                </configuration>
            </plugin>
            <!--            Test koştuktan sonra rapor çıktısı için gerekli plugin -->
            <plugin>
                <groupId>net.serenity-bdd.maven.plugins</groupId>
                <artifactId>serenity-maven-plugin</artifactId>
                <version>2.4.4</version>
                <executions>
                    <execution>
                        <id>serenity-reports</id>
                        <phase>post-integration-test</phase>
                        <goals>
                            <goal>aggregate</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <!--         Raporu tüm testler koştuktan sonra almak istediğimizi belirtiyoruz -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.0.0-M5</version>
                <configuration>
                    <testFailureIgnore>true</testFailureIgnore>
                </configuration>
            </plugin>
        </plugins>
    </build>
Ekleme yaptıktan sonra pom.xml inize sağ tuş yapıp maven reload projects deyin.


5. src/test/java altına patika adında bir paket oluşturuyoruz.
   Patika paketinin altına n11 paketi ve onunda altına admin paketi oluşturuyoruz.

Bu paketin altına da N11AdminGetTest adında bir sınıf oluşturuyoruz.


6.@Test yazıp getAllN11 rest api metodunu oluşturun ve bir request gönderin.

7.Serenity raporunun anlayabilmesi adına bu sıradan bir test sınıfıdır.

Sınıf seviyesinde bu anotasyonu yazın : @SerenityTest
import net.serenitybdd.junit5.SerenityTest;
Bu notasyonun import satırı yukarıdaki gibi olmalı.
Proje seviyesinde serenity.properties adında bir property dosyası ekleyin.
Property dosyasının içerisine aşağıdakileri ekleyin:
serenity.project.name=Patika API Report
serenity.test.root=patika

9.In order to generate serenity report, we need to use maven goal
Serenity raporu oluşturmak için maven goal kullanacağız.
Komut satırında mvn clean verify yapın. Sonrasında Mac ise pc niz cmd + enter
Windows ise ctrl + enter yapın.
Target dizini oluşacak.
Eğer hata verirse aşağıdaki gibi yapın
Yada intellij üzerinden  maven sekmesi var orada sağ tarafta.
Orayı açıp clean sonra verify butonlarına tıklayın.
Rapor html olarak target dizinin altına gelecek.
Orada site diye bi dizinde bir çok html dosyası göreceksiniz index.html ye sağ tuş yapıp herhangi bir tarayıcı ile açın.

10.Bu dependency serenity loglarını yazdırmak ve warning leri görmek için gerekli bunu da dependencies kısmına ekleyin.

<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-simple</artifactId>
    <version>1.7.30</version>
</dependency>

Test raporunu ürettik ancak request ve response a dair bir detay göremedik, detay görmek istiyorsak given(), when() then() metodlarını kullanırız.

Instead of importing rest assured given import,use below
given() yazdığımızda rest assured un given imports yerine aşağıdaki static import u kullanın.

import static net.serenitybdd.rest.SerenityRest.given;


Bu noktadan sonra serenity raporda tüm detayları göreceksiniz, testlerin üzerinde de Rest Query butonunu göreceksiniz.
Serenity rapor içerisinde assert etmek ve onu bir adım olarak göstermek Ensure sınıfı ile yapılır.
(from import net.serenitybdd.rest.Ensure;)

        Ensure.that("Status code is 200",validatableResponse -> validatableResponse.statusCode(200) );

        Ensure.that("Content-type is JSON",vRes -> vRes.contentType(ContentType.JSON));

        Ensure.that("sub_id is your-user-1234", vRes -> vRes.body("sub_id is",is("your-user-1234")));
