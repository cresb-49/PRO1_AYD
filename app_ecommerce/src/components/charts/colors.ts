export enum ColorsChart {
  Red = '#FF6384',
  Magenta = '#42A5F5',
  Blue = '#36A2EB',
  Yellow = '#FFCE56',
  Green = '#4BC0C0',
  Purple = '#9966FF',
  Orange = '#FF9F40',
  Teal = '#4BC0C0',
  DarkBlue = '#2F4F4F',
  LightGreen = '#7FFF00',
  LightCoral = '#F08080',
  DarkOrange = '#FF8C00',
  SkyBlue = '#87CEEB',
  Pink = '#FFC0CB',
  Lime = '#00FF00',
  Navy = '#000080',
  Brown = '#A52A2A',
  Violet = '#EE82EE',
  Gold = '#FFD700'
}

//Esportamos una funcion que retorna un color aleatorio pero recibe un color
// para que no se repita el color en la grafica
export function getRandomColor(color: ColorsChart | null): ColorsChart {
  let randomColor = color
  if (randomColor === null) {
    return Object.values(ColorsChart)[Math.floor(Math.random() * Object.values(ColorsChart).length)]
  }
  while (randomColor === color) {
    randomColor =
      Object.values(ColorsChart)[Math.floor(Math.random() * Object.values(ColorsChart).length)]
  }
  return randomColor
}
