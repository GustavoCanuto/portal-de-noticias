import { environment } from "../../../environments/environments.development";

describe('Campo de busca', () => {
  it('Verificar responsividade do campo de busca', () => {
    cy.viewport(375, 667) 

    cy.visit(`${environment.urlSite}`)
    cy.get('[data-test="abrirBuscaMobile"]').click();
    cy.get('[data-test="inputBusca"]');
    cy.get('[data-test="fecharBuscaMobile"]').click();
    cy.get('[data-test="inputBusca"]').should('not.be.visible');
  })

  it('Verificar funcionamento do campo de busca mobile', () => {
    cy.viewport(375, 667) 

    cy.visit(`${environment.urlSite}`)
    cy.get('[data-test="abrirBuscaMobile"]').click();
    cy.get('[data-test="inputBusca"]').type('Saude')
    cy.get('[data-test="inputBusca"]').type('{enter}')
    cy.url().should('eq', 'http://192.168.15.189:4200/busca/Saude')
    cy.get('[data-test="resultadoBusca"]')
  })

  it('Verificar funcionamento do campo de busca desktop', () => {
    cy.viewport(1280, 800) 

    cy.visit(`${environment.urlSite}`)
    cy.get('[data-test="inputBusca"]').type('Saude')
    cy.get('[data-test="inputBusca"]').type('{enter}')
    cy.url().should('eq', 'http://192.168.15.189:4200/busca/Saude')
    cy.get('[data-test="resultadoBusca"]')
  })
})