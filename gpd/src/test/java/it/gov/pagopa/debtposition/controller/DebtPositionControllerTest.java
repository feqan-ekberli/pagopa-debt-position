package it.gov.pagopa.debtposition.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import it.gov.pagopa.debtposition.DebtPositionApplication;
import it.gov.pagopa.debtposition.TestUtil;
import it.gov.pagopa.debtposition.mock.DebtorDTOMock;
import it.gov.pagopa.debtposition.service.DebtPositionService;


@SpringBootTest(classes = DebtPositionApplication.class)
@AutoConfigureMockMvc
class DebtPositionControllerTest {
    
    @Autowired
    private MockMvc mvc;
    
    @Mock
    private ModelMapper modelMapperMock;
    
    @Mock
    private DebtPositionService debtPositionService;

    
    @BeforeEach
    void setUp() {
    }
    
    // CREATE DEBT POSITION 
    @Test
    void createDebtPosition_201() throws Exception {
        mvc.perform(post("/organizations/12345678901/debtpositions")
                .content(TestUtil.toJson(DebtorDTOMock.getMock1()))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
    }
    
    @Test
    void createDebtPosition_Multiple_201() throws Exception {
        mvc.perform(post("/organizations/MULTIPLE_12345678901/debtpositions")
                .content(TestUtil.toJson(DebtorDTOMock.getMultiplePPMock()))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
    }
    
    @Test
    void createDebtPosition_400() throws Exception {
        mvc.perform(post("/organizations/400_12345678901/debtpositions")
                .content(TestUtil.toJson(DebtorDTOMock.get400Mock()))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    
    @Test
    void createDebtPosition_409() throws Exception {
        mvc.perform(post("/organizations/409_12345678901/debtpositions")
                .content(TestUtil.toJson(DebtorDTOMock.getMock2()))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        
        // provo a creare 2 posizioni debitorie con lo stesso organization_fiscal_code 
        // => la seconda chiamata deve andare in errore con codice 409
        mvc.perform(post("/organizations/409_12345678901/debtpositions")
                .content(TestUtil.toJson(DebtorDTOMock.getMock3()))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    
    @Test
    void createDebtPosition_500() throws Exception {
        mvc.perform(post("/organizations/500_12345678901/debtpositions")
                .content(TestUtil.toJson(DebtorDTOMock.get500Mock1()))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    
    // GET DEBT POSITION BY IUPD
    @Test
    void getDebtPositionByIUPD() throws Exception {
        // creo una posizione debitoria e la recupero
        mvc.perform(post("/organizations/200_12345678901/debtpositions")
                .content(TestUtil.toJson(DebtorDTOMock.getMock4()))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
        
        String url = "/organizations/200_12345678901/debtpositions/12345678901IUPDMOCK1";
        mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    
    @Test
    void getDebtPositionByIUPD_404() throws Exception {
        String url = "/organizations/200_12345678901/debtpositions/12345678901IUPDNOTEXIST";
        mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}
