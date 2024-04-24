import winston from "winston";
import chalk from "chalk";


const coloredFormat = winston.format.printf((info) => {
    const { timestamp, level, message } = info;
    let levelColorized;
    if (level === "error") {
      levelColorized = chalk.red(level);
    } else if (level === "warn") {
      levelColorized = chalk.yellow(level);
    } else if (level === "info") {
      levelColorized = chalk.blue(level);
    } else {
      levelColorized = chalk.white(level);
    }
    return `${timestamp} ${levelColorized}: ${message}`;
  });
  
  // Crie uma instância do winston e adicione o transportador do LoggingWinston
 const logger = winston.createLogger({
    level: "info",
    format: winston.format.combine(
      winston.format.timestamp(), // Inclui o timestamp no log
      winston.format.printf(
        (info) => `${info.timestamp} ${info.level}: ${info.message}`
      ),
      coloredFormat // Formato personalizado para a saída do log
    ),
    transports: [new winston.transports.Console()],
  });

  export default logger;