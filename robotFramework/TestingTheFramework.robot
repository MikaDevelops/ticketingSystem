*** Settings ***
Library    SeleniumLibrary
Library    String

*** Variables ***
${site-url}        http://localhost:8080
${browser-to-use}  firefox
*** Test Cases ***
Create A Random String
    Log To Console    We are going to generate a random string
    Generate Random String  10
    Log To Console    We finished generating a random string

Incidents load to the page
    Open Browser
    [Teardown]


*** Keywords ***
Should load incident nr1
    Open Browser    ${site-url}    ${browser-to-use}

