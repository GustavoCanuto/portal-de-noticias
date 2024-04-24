import { environment } from "../../../environments/environments.development";

describe('Menu Hamburguer', () => {
    beforeEach(() => {
        cy.visit(`${environment.urlSite}`)
        cy.get('[data-test="iconeMenu"]').click();
    })

    it('Verificar se menu abre e fecha', () => {
        cy.get('[data-test="menu"]');
        cy.get('[data-test="fecharMenu"]').click();
        cy.get('[data-test="menu"]').should('not.exist');
    })

    it('Verificar funcionamento do menu', () => {
        cy.get('[data-test="categoria"]').then(($categoria) => {
            const randomIndex = Math.floor(Math.random() * $categoria.length);
            const $randomCard = $categoria.eq(randomIndex);
            cy.wrap($randomCard).click();
            cy.get('[data-test="loading"]').should('not.exist')
            cy.get('[data-test="resultadoBusca"]');
        })
    })

    it('Verificar redirecionamento sobre nos', () => {
        cy.get('[data-test="sobreNos"]').click();
        cy.url().should('eq', 'http://192.168.15.189:4200/sobre-nos');
        cy.get('[data-test="sobreNos"]');
    })
})