package com.example.security.converter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.example.security.entity.Certificate;

public class CertificateConverter extends AbstractConverter{
	
    public static List<Certificate> fromMapToCertificate(Map<String, String> map) {
        List<Certificate> certificates = new ArrayList<>();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            certificates.add(new Certificate(0L, (String) pair.getKey(),
                    (String) pair.getValue(), true));
            it.remove();
        }
        return certificates;
    }

}
