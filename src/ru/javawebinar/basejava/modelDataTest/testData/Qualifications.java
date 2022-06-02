package ru.javawebinar.basejava.modelDataTest.testData;

import ru.javawebinar.basejava.model.sections.ListStringSection;

import java.util.ArrayList;

public class Qualifications {
    public static ListStringSection createSection() {
        return new ListStringSection(new ArrayList<>() {{
            add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
            add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        }});
    }
}
