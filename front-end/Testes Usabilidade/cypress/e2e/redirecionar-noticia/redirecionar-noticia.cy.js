import { environment } from "../../../environments/environments.development";

describe('Redirecionar para tela da noticia', () => {
  beforeEach(() => {
    cy.visit(`${environment.urlSite}`)
    cy.get('[data-test="loading"]').should('not.exist')
  })

  it('Verificar se houve o carregamento da noticia pelos cards', () => {
    cy.get('[data-test="cardNoticia"]').then(($cards) => {
      const randomIndex = Math.floor(Math.random() * $cards.length)
      const $randomCard = $cards.eq(randomIndex)
      cy.wrap($randomCard).click()

      cy.get('[data-test="tituloNoticia"]')
      cy.get('[data-test="conteudoNoticia"]')
      cy.get('[data-test="linkNoticia"]')
    })
  })

  it('Verificar se houve o carregamento da noticia pelo carrossel', () => {
    cy.get('[data-test="noticiaCarrossel"]').then(($cards) => {
      const randomIndex = Math.floor(Math.random() * $cards.length)
      const $randomCard = $cards.eq(randomIndex)
      cy.wrap($randomCard).click()

      cy.get('[data-test="tituloNoticia"]')
      cy.get('[data-test="conteudoNoticia"]')
      cy.get('[data-test="linkNoticia"]')
    })
  })

  it('Verificar se houve o carregamento da noticia pelas principais', () => {
    cy.get('[data-test="noticiaPrincipal"]').then(($cards) => {
      const randomIndex = Math.floor(Math.random() * $cards.length)
      const $randomCard = $cards.eq(randomIndex)
      cy.wrap($randomCard).click()

      cy.get('[data-test="tituloNoticia"]')
      cy.get('[data-test="conteudoNoticia"]')
      cy.get('[data-test="linkNoticia"]')
    })
  })

  it('Verificar se houve o carregamento da noticia correta pelos cards', () => {
    cy.get('[data-test="cardNoticia"]').then(($cards) => {
      const randomIndex = Math.floor(Math.random() * $cards.length)
      const $randomCard = $cards.eq(randomIndex)
      const tituloNoticia = $randomCard[0].children[1].children[1].innerText;
      cy.wrap($randomCard).click()

      cy.get('[data-test="tituloNoticia"]').contains(`${tituloNoticia}`)
      cy.get('[data-test="conteudoNoticia"]')
      cy.get('[data-test="linkNoticia"]')
    })
  })

  it('Verificar se houve o carregamento da noticia correta pela principais', () => {
    cy.get('[data-test="noticiaPrincipal"]').then(($cards) => {
      const randomIndex = Math.floor(Math.random() * $cards.length)
      const $randomCard = $cards.eq(randomIndex)

      console.log($randomCard);
      const tituloNoticia = $randomCard[0].children[0].innerText;
      cy.wrap($randomCard).click()

      cy.get('[data-test="tituloNoticia"]').contains(`${tituloNoticia}`)
      cy.get('[data-test="conteudoNoticia"]')
      cy.get('[data-test="linkNoticia"]')
    })
  })

  it('Verificar se houve o carregamento da noticia correta pelo carrossel', () => {
    cy.get('[data-test="noticiaCarrossel"]').then(($cards) => {
      const randomIndex = Math.floor(Math.random() * $cards.length)
      const $randomCard = $cards.eq(randomIndex)
      console.log($randomCard);

      const tituloNoticia = $randomCard[0].children[2].innerText;
      cy.wrap($randomCard).click()

      cy.get('[data-test="tituloNoticia"]').contains(`${tituloNoticia}`)
      cy.get('[data-test="conteudoNoticia"]')
      cy.get('[data-test="linkNoticia"]')
    })
  })
})