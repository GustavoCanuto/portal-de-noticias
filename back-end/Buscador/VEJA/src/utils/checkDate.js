export default function isDataDoDiaAnterior(data) {
  if(data && data!=null){
    const dataPublicacao = new Date(data);
    const hoje = new Date();
    const ontem = new Date(hoje);
    ontem.setDate(ontem.getDate() - 5); // pegar noticias de até dois dias atras
    ontem.setHours(0, 0, 0, 0); // pegar so noticias de hoje
  // console.log("data pub:"+ dataPublicacao)
  //  console.log("data 2 dias:"+ ontem+"fim ****\n")
    return (
      dataPublicacao<= ontem
    );
    }else{
      return false;
    }
  }
  
