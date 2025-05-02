*** Settings ***
Library    SeleniumLibrary

*** Variables ***
${site-url}        http://localhost:8080
${browser-to-use}  firefox
*** Test Cases ***
Create A Random String
    Log To Console    We are going to generate a random string
    Generate Random String  10
    Log To Console    We finished generating a random string

Incidents load to the page
    Should load incident nr 1
    [Teardown]


*** Keywords ***
Should load incident nr 1
    Open Browser    ${site-url}    ${browser-to-use}

