export default async function getDataNoticia(pageInstance) {
  try {
    const { dataElement } = await pageInstance.evaluate(() => {
      const dataElement = document.querySelector(
        ".post-header .author> span"
      ).textContent;
      return { dataElement };
    });

    let atualizacaoMatch = dataElement
      .trim()
      .match(/Atualizado em (\d{1,2}) ([a-z]{3}) (\d{4}), (\d{1,2})h(\d{2})/i);
    let publicacaoMatch = dataElement
      .trim()
      .match(/Publicado em (\d{1,2}) ([a-z]{3}) (\d{4}), (\d{1,2})h(\d{2})/i);

    let dataAtualizacao = null;
    let dataPublicacao = null;

    const months = {
      jan: "01",
      fev: "02",
      mar: "03",
      abr: "04",
      mai: "05",
      jun: "06",
      jul: "07",
      ago: "08",
      set: "09",
      out: "10",
      nov: "11",
      dez: "12",
    };

    if (atualizacaoMatch) {
      const month = months[atualizacaoMatch[2].toLowerCase()];
      dataAtualizacao = `${
        atualizacaoMatch[3]
      }-${month}-${atualizacaoMatch[1].padStart(2, "0")}T${
        atualizacaoMatch[4]
      }:${atualizacaoMatch[5]}:00.000Z`;
    }

    if (publicacaoMatch) {
      const month = months[publicacaoMatch[2].toLowerCase()];
      dataPublicacao = `${
        publicacaoMatch[3]
      }-${month}-${publicacaoMatch[1].padStart(2, "0")}T${publicacaoMatch[4]}:${
        publicacaoMatch[5]
      }:00.000Z`;
    } else {
      let publicacaoMatch = dataElement.match(
        /(\d{1,2}) ([a-z]{3}) (\d{4}), (\d{1,2})h(\d{2})/i
      );
      if (publicacaoMatch) {
        const month = months[publicacaoMatch[2].toLowerCase()];
        dataPublicacao = `${
          publicacaoMatch[3]
        }-${month}-${publicacaoMatch[1].padStart(2, "0")}T${
          publicacaoMatch[4]
        }:${publicacaoMatch[5]}:00.000Z`;
      }
    }

    return { dataPublicacao, dataAtualizacao };
  } catch (error) {
    console.error(error);
    return { dataPublicacao: null, dataAtualizacao: null };
  }
}
