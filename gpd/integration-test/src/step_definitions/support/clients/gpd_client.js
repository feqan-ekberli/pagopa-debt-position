const { get, post, del } = require("../utility/axios_common");
const fs = require("fs");

const gpd_host = process.env.gpd_host;

function gpdHealthCheck() {
    return get(gpd_host + `/info`, {
        headers: {
            "Ocp-Apim-Subscription-Key": process.env.GPD_SUBSCRIPTION_KEY
        }
    })
}

function createDebtPosition(orgId, body) {
    return post(gpd_host + `/organizations/${orgId}/debtpositions`, body, {
        timeout: 10000,
        headers: {
            "Ocp-Apim-Subscription-Key": process.env.GPD_SUBSCRIPTION_KEY,
            "Content-Type": "application/json"
        }
    })
}

function publishDebtPosition(orgId, iupd) {
    return post(gpd_host + `/organizations/${orgId}/debtpositions/${iupd}/publish`, "", {
        timeout: 10000,
        headers: {
            "Ocp-Apim-Subscription-Key": process.env.GPD_SUBSCRIPTION_KEY,
            "Content-Type": "application/json"
        }
    })
}

function getDebtPositionList(orgId) {
    return get(gpd_host + `/organizations/${orgId}/debtpositions/`, {
        timeout: 10000,
        params: {
            due_date_from: "2023-03-07",
            due_date_to: "2023-04-06",
            orderby: "INSERTED_DATE",
            ordering: "ASC",
            page: 0
        },
        headers: {
            "Ocp-Apim-Subscription-Key": process.env.GPD_SUBSCRIPTION_KEY,
            "Content-Type": "application/json"
        }
    })
}

function deleteDebtPosition(orgId, iupd) {
    return del(gpd_host + `/organizations/${orgId}/debtpositions/${iupd}/publish`, "", {
        timeout: 10000,
        headers: {
            "Ocp-Apim-Subscription-Key": process.env.GPD_SUBSCRIPTION_KEY,
            "Content-Type": "application/json"
        }
    })
}

module.exports = {
    createDebtPosition,
    gpdHealthCheck,
    publishDebtPosition,
    getDebtPositionList,
    deleteDebtPosition,
}