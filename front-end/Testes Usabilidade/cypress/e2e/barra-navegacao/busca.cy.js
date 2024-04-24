import { environment } from "../../../environments/environments.development";

describe('Teste de busca por portal ', () => {
  it('Teste de busca pela G1', () => {
    cy.visit(`${environment.urlSite}`);
    cy.get('[data-test="portal"]').contains('G1').click();
    cy.url().should('eq', 'http://192.168.15.189:4200/noticias/G1');

    cy.get('[data-test="loading"]').should('not.exist')
    cy.get('[data-test="iconePortal"]').contains('G1')
  })

  it('Teste de busca pela UOL', () => {
    cy.visit(`${environment.urlSite}`);
    cy.get('[data-test="portal"]').contains('UOL').click();
    cy.url().should('eq', 'http://192.168.15.189:4200/noticias/UOL');

    cy.get('[data-test="loading"]').should('not.exist')
    cy.get('[data-test="iconePortal"]').contains('UOL')
  })

  it('Teste de busca pela CNN', () => {
    cy.visit(`${environment.urlSite}`);
    cy.get('[data-test="portal"]').contains('CNN').click();
    cy.url().should('eq', 'http://192.168.15.189:4200/noticias/CNN');

    cy.get('[data-test="loading"]').should('not.exist')
    cy.get('[data-test="iconePortal"]').contains('CNN')
  })

  it('Teste de busca pela GOV', () => {
    cy.visit(`${environment.urlSite}`);
    cy.get('[data-test="portal"]').contains('GOV').click();
    cy.url().should('eq', 'http://192.168.15.189:4200/noticias/GOV');

    cy.get('[data-test="loading"]').should('not.exist')
    cy.get('[data-test="iconePortal"]').contains('GOV')
  })

  it('Teste de busca pela VEJA', () => {
    cy.visit(`${environment.urlSite}`);
    cy.get('[data-test="portal"]').contains('VEJA').click();
    cy.url().should('eq', 'http://192.168.15.189:4200/noticias/VEJA');

    cy.get('[data-test="loading"]').should('not.exist')
    cy.get('[data-test="iconePortal"]').contains('VEJA')
  })
})