package com.test;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.halcyoninae.halcyon.cacher.Cacher;
import com.halcyoninae.halcyon.debug.Debugger;

public class TestXML {

  /**
   * @param args
   * @throws Exception
   */
  public static void main(String ... args) throws Exception {
    Cacher cacher = new Cacher(new File("/home/jackm/Code/mp4j-test/src/com/test/test.halcyon"));
    Map<String, String> content = new HashMap<>();
    content.put("Amogus", "Sus");
    content.put("Sus", "sisu\n\n\n\n\n\nsusususufsdfsdfsdfsdfsdfsdfsusuususs");

    cacher.build("amogus", content);

    Debugger.good(cacher.getContent("Sus"));

  }
}
