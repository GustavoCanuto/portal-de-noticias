
export default async function acessaLink(link,pageInstance){
    await pageInstance.goto(link, {
      timeout: 50000,
      waitUntil: "load",
    });
  }