package org.vin.locations.cucumber.stepdefs;

import org.vin.locations.LocationsCasReactApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = LocationsCasReactApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
