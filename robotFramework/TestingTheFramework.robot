*** Settings ***
Library    SeleniumLibrary

*** Variables ***
${site-url}        http://localhost:8080
${browser-to-use}  firefox
*** Test Cases ***
Incidents load to the page
    Open browser to incidents page

    [Teardown]


*** Keywords ***
Open browser to incidents page
    Open Browser    ${site-url}    browser=${browser-to-use}


