import { environment } from "../../../environments/environments.development";

describe('Página Inicial', () => {
  it('Verificar se houve o carregamento de todos os itens corretamente', () => {
    cy.visit(`${environment.urlSite}`)

    cy.get('[data-test="loading"]').should('not.exist')

    cy.get('[data-test="noticiaPrincipal"]')
    cy.get('[data-test="noticiaCarrossel"]')
    cy.get('[data-test="cardNoticia"]')
  })
})