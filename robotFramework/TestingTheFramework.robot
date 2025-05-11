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

Incidents opens to caseview
    Open browser to incidents page
    Incident 1 should be on caseview
    Incident 2 should be on caseview
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

Incident 1 should be on caseview
    Click Element    id:case-list >> id:1
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[2]/td[2]   1
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[3]/td[2]   15120
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[4]/td[2]   test subject
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[5]/td[2]   test description
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[6]/td[2]   test notes
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[7]/td[2]   1,2,3,4
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[8]/td[2]   1
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[9]/td[2]   1
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[10]/td[2]   1
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[11]/td[2]   1
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[12]/td[2]   new
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[13]/td[2]   Coffee on keyboard dryed using hairdryer.
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[14]/td[2]   Workstation
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[15]/td[2]   Patrick Star
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[16]/td[2]   John
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[17]/td[2]   Milton
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[18]/td[2]   Holmes
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[19]/td[2]   normal

Incident 2 should be on caseview
    Click Element    id:case-list >> id:2
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[2]/td[2]   2
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[3]/td[2]   15121
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[4]/td[2]   test subject442
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[5]/td[2]   test description442
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[6]/td[2]   test note33s2
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[7]/td[2]   2,3
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[8]/td[2]   2
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[9]/td[2]   2
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[10]/td[2]   2
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[11]/td[2]   2
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[12]/td[2]   under work
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[13]/td[2]   Powercord plugged to wall outlet
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[14]/td[2]   Server,Deep fryer
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[15]/td[2]   Mr. Krabs,Patrick Star
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[16]/td[2]   Michelle
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[17]/td[2]   Eleanore
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[18]/td[2]   Pfiifferi
    Element Text Should Be    id:casedata >> tag:table >> xpath://tr[19]/td[2]   high