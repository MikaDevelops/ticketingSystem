*** Settings ***
Library    SeleniumLibrary

*** Variables ***
${site-url}        http://localhost:8080
${browser-to-use}  Firefox

*** Test Cases ***
Incidents load to the page
    Open browser to incidents page
    Incident 1 should be on list
    Incident 2 should be on list
    Incident 3 should be on list
    Incident 4 should be on list
    [Teardown]    Close Browser


*** Keywords ***
Open browser to incidents page
    Open Browser    ${site-url}    browser=${browser-to-use}
    Title Should Be    Ticketing System
Incident 1 should be on list
    Element Text Should Be    id:case-list >> id:1    test subject
Incident 2 should be on list
    Element Text Should Be    id:case-list >> id:2    test subject442
Incident 3 should be on list
    Element Text Should Be    id:case-list >> id:3    test subject443
Incident 4 should be on list
    Element Text Should Be    id:case-list >> id:4    test subject444
