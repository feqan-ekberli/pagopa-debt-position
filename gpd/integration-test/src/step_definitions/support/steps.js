const { Given, When, Then, AfterAll, Before } = require('@cucumber/cucumber')
const { executeHealthCheckForGPD } = require('./logic/health_checks_logic');
const { executeDebtPositionCreation, executeDebtPositionDeletion } = require('./logic/gpd_logic');
const { assertAmount, assertFaultCode, assertOutcome, assertStatusCode, executeAfterAllStep, randomOrg, randomIupd } = require('./logic/common_logic');
const { gpdSessionBundle } = require('./utility/data');
const { getValidBundle } = require('./utility/helpers');

let idOrg;
let iupd;

/*
 *  'Given' precondition for health checks on various services.
 */
Given('GPD running', () => executeHealthCheckForGPD());


/*
 *  Debt position creation
 */
Given('a random organization id', async function () {
    idOrg = randomOrg();
    iupd = randomIupd();
    // precondition -> deletion possible dirty data
    await executeDebtPositionDeletion(idOrg, iupd);
    });
When('the debt position is created', () => executeDebtPositionCreation(gpdSessionBundle, idOrg, iupd));
Then('the debt position gets the status code {int}', (statusCode) => assertStatusCode(getValidBundle(gpdSessionBundle, gpsSessionBundle), statusCode));
