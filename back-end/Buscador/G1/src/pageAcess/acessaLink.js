
export default async function acessaLink(link,pageInstance){
    await pageInstance.goto(link, {
      timeout: 70000,
      waitUntil: "load",
    });
  }